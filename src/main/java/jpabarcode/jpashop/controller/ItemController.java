package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.domain.item.Item;
import jpabarcode.jpashop.file.FileStore;
import jpabarcode.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;

    //상품목록 조회
    @GetMapping("/items/{category}")
    public String items(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model, @PathVariable String category, @RequestParam(value = "name", required = false) String name) {
        String category1 = "";
        String category2 = "";

        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        //카테고리별 상품목록 조회
        if (category.equals("thermalPrinter")) {        //열전사프린터
            category1 = "UCS";
            category2 = "열전사프린터";
        }else if (category.equals("labelPrinter")) {    //라벨프린터
            category1 = "UCS";
            category2 = "라벨프린터";
        }else if (category.equals("inLine")) {          //in-line 12/25
            category1 = "EDDING";
            category2 = "in-line 12/25";
        }else if (category.equals("inLinePro")) {       //in-line pro 12/25
            category1 = "EDDING";
            category2 = "in-line pro 12/25";
        }else if (category.equals("lcx")) {             //LCX
            category1 = "MACSA";
            category2 = "LCX";
        }
        List<Item> items = itemService.findByCategory(category1, category2, name);
        model.addAttribute("items", items);
        model.addAttribute("category", category);
        model.addAttribute("category1", category1);
        model.addAttribute("category2", category2);

        ItemForm searchForm = new ItemForm();
        searchForm.setCategory1(category1);
        searchForm.setCategory2(category2);
        searchForm.setName(name);
        model.addAttribute("searchForm", searchForm);
        return "items/productList";
    }

    @GetMapping("/itemsGrid")
    public String itemsGrid(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model) {
        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/productListGrid";
    }

    //상품상세 조회
    @GetMapping("/items/{itemId}/view")
    public String itemView(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, @PathVariable("itemId") Long itemId, Model model) {
        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        Item item = itemService.findOne(itemId);
        model.addAttribute("item", item);
        model.addAttribute("category1", item.getCategory1());
        model.addAttribute("category2", item.getCategory2());

        String category = "";

        switch (item.getCategory2()) {
            case "열전사프린터":         //열전사프린터
                category = "thermalPrinter";
                break;
            case "라벨프린터":     //라벨프린터
                category = "labelPrinter";
                break;
            case "in-line 12/25":           //in-line 12/25
                category = "inLine";
                break;
            case "in-line pro 12/25":        //in-line pro 12/25
                category = "inLinePro";
                break;
            case "LCX":              //LCX
                category = "lcx";
                break;
        }
        model.addAttribute("category", category);

        return "items/productDetail";
    }

    //상품등록 화면 조회(관리자기능)
    @GetMapping("/items/new")
    public String createForm(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model) {
        //세션에 회원데이터가 없으면 제품소개 페이지로 이동
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "redirect:/items";
        }
        model.addAttribute("member", loginMember);
        model.addAttribute("form", new Item());
        return "items/createForm";
    }

    //상품등록
    @PostMapping("/items/new")
    public String create(ItemForm form) throws IOException {
        itemService.save(form);
        return "redirect:/items";
    }

    //상품수정 화면 조회
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setContent(item.getContent());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", form);
        return "items/updateForm";
    }

    //상품수정
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") ItemForm form) throws IOException {
        itemService.updateItem(form);
        return "redirect:/items";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

//    @GetMapping("/attach/{itemId}")
//    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
//        Item item = itemService.findOne(itemId);
//        String storeFileName = item.getAttachFile().getStoreFileName();
//        String uploadFileName = item.getAttachFile().getUploadFileName();
//
//        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
//
//        log.info("uploadFileName={}", uploadFileName);
//
//        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(resource);
//    }

}
