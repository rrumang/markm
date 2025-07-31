package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.Board;
import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.domain.item.Item;
import jpabarcode.jpashop.file.FileStore;
import jpabarcode.jpashop.service.BoardService;
import jpabarcode.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final BoardService boardService;
    private final ItemService itemService;
    private final FileStore fileStore;

    //배너관리
    @RequestMapping("/admin/banner")
    public String createBannerForm (@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model ) {
        log.info("banner controller");
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        model.addAttribute("member", loginMember);

        // 배너이미지 조회
        List<Board> banners = boardService.findByType("banner");
        int bannerCount = banners.size();
        System.out.println("bannerCount : " + bannerCount);
        if(bannerCount == 0) {
            model.addAttribute("banner1", new Board());
            model.addAttribute("banner2", new Board());
            model.addAttribute("banner3", new Board());
            model.addAttribute("banner4", new Board());
            model.addAttribute("banner5", new Board());
        }else if(bannerCount == 1) {
            model.addAttribute("banner1", banners.get(0));
            model.addAttribute("banner2", new Board());
            model.addAttribute("banner3", new Board());
            model.addAttribute("banner4", new Board());
            model.addAttribute("banner5", new Board());
        }else if(bannerCount == 2) {
            model.addAttribute("banner1", banners.get(0));
            model.addAttribute("banner2", banners.get(1));
            model.addAttribute("banner3", new Board());
            model.addAttribute("banner4", new Board());
            model.addAttribute("banner5", new Board());
        }else if(bannerCount == 3) {
            model.addAttribute("banner1", banners.get(0));
            model.addAttribute("banner2", banners.get(1));
            model.addAttribute("banner3", banners.get(2));
            model.addAttribute("banner4", new Board());
            model.addAttribute("banner5", new Board());
        }else if(bannerCount == 4) {
            model.addAttribute("banner1", banners.get(0));
            model.addAttribute("banner2", banners.get(1));
            model.addAttribute("banner3", banners.get(2));
            model.addAttribute("banner4", banners.get(3));
            model.addAttribute("banner5", new Board());
        }else if(bannerCount == 5) {
            model.addAttribute("banner1", banners.get(0));
            model.addAttribute("banner2", banners.get(1));
            model.addAttribute("banner3", banners.get(2));
            model.addAttribute("banner4", banners.get(3));
            model.addAttribute("banner5", banners.get(4));
        }

        model.addAttribute("menu1", "banner");
        return "admin/banner";
    }

    //배너등록
    @PostMapping("/admin/banner/new")
    public String createBanner(BoardForm form) throws IOException {

        //첫번째 배너
        BoardForm banner1 = new BoardForm();
        banner1.setType("banner");
        banner1.setId(form.getId1());
        banner1.setTitle(form.getTitle1());
        banner1.setContent(form.getContent1());
        banner1.setFileName(form.getFileName1());
        if (!form.getAttachFile1().isEmpty()) {
            //기존파일이 있다면 삭제
            if(!form.getFileName1().isEmpty()) fileStore.deleteFile(form.getFileName1());
            banner1.setAttachFile(form.getAttachFile1());
        }
        boardService.save(banner1);

        //두번째 배너
        BoardForm banner2 = new BoardForm();
        banner2.setType("banner");
        banner2.setId(form.getId2());
        banner2.setTitle(form.getTitle2());
        banner2.setContent(form.getContent2());
        banner2.setFileName(form.getFileName2());
        if (!form.getAttachFile2().isEmpty()) {
            //기존파일이 있다면 삭제
            if(!form.getFileName2().isEmpty()) fileStore.deleteFile(form.getFileName2());
            banner2.setAttachFile(form.getAttachFile2());
        }
        boardService.save(banner2);

        //세번째 배너
        BoardForm banner3 = new BoardForm();
        banner3.setType("banner");
        banner3.setId(form.getId3());
        banner3.setTitle(form.getTitle3());
        banner3.setContent(form.getContent3());
        banner3.setFileName(form.getFileName3());
        if (!form.getAttachFile3().isEmpty()) {
            //기존파일이 있다면 삭제
            if(!form.getFileName3().isEmpty()) fileStore.deleteFile(form.getFileName3());
            banner3.setAttachFile(form.getAttachFile3());
        }
        boardService.save(banner3);

        //네번째 배너
        BoardForm banner4 = new BoardForm();
        banner4.setType("banner");
        banner4.setId(form.getId4());
        banner4.setTitle(form.getTitle4());
        banner4.setContent(form.getContent4());
        banner4.setFileName(form.getFileName4());
        if (!form.getAttachFile4().isEmpty()) {
            //기존파일이 있다면 삭제
            if(!form.getFileName4().isEmpty()) fileStore.deleteFile(form.getFileName4());
            banner4.setAttachFile(form.getAttachFile4());
        }
        boardService.save(banner4);

        //다섯번째 배너
        BoardForm banner5 = new BoardForm();
        banner5.setType("banner");
        banner5.setId(form.getId5());
        banner5.setTitle(form.getTitle5());
        banner5.setContent(form.getContent5());
        banner5.setFileName(form.getFileName5());
        if (!form.getAttachFile5().isEmpty()) {
            //기존파일이 있다면 삭제
            if(!form.getFileName5().isEmpty()) fileStore.deleteFile(form.getFileName5());
            banner5.setAttachFile(form.getAttachFile5());
        }
        boardService.save(banner5);

        return "redirect:/";
    }

    //CEO인사말 조회
    @RequestMapping("/admin/about")
    public String createAboutForm (@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model ) {
        log.info("about controller");
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        model.addAttribute("member", loginMember);

        // CEO인사말 조회
        List<Board> aboutList = boardService.findByType("about");
        int aboutCount = aboutList.size();
        Board about = new Board();
        about.setType("about");
        about.setContent("");
        about.setTitle("CEO인사말");
        if(aboutCount == 1) {
            about.setId(aboutList.get(0).getId());
            about.setContent(aboutList.get(0).getContent());
        }
        model.addAttribute("about", about);
        return "admin/about";

    }

    //CEO인사말 등록
    @PostMapping("/admin/about/new")
    public String createAbout(BoardForm form) throws IOException {
        boardService.save(form);
        return "redirect:/";
    }

    //찾아오시는길 조회
    @RequestMapping("/admin/about2")
    public String createAbout2Form (@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model ) {
        log.info("about2 controller");
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        model.addAttribute("member", loginMember);

        //찾아오시는길 조회
        List<Board> about2List = boardService.findByType("about2");
        int about2Count = about2List.size();
        Board about2 = new Board();
        about2.setType("about2");
        about2.setContent("");
        about2.setTitle("찾아오시는길");
        if(about2Count == 1) {
            about2.setId(about2List.get(0).getId());
            about2.setContent(about2List.get(0).getContent());
        }
        model.addAttribute("about2", about2);
        return "admin/about2";

    }

    //찾아오시는길 등록
    @PostMapping("/admin/about2/new")
    public String createAbout2(BoardForm form) throws IOException {
        boardService.save(form);
        return "redirect:/";
    }

    //제품 조회
    @GetMapping("/admin/items/{category}")
    public String adminTtems(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model, @PathVariable String category, @RequestParam(value = "category2", required = false) String category2, @RequestParam(value = "name", required = false) String name) {
        log.info("item controller");
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }

        //카테고리별 상품목록 조회
        List<Item> items = itemService.findByCategory(category, category2, name);

        model.addAttribute("member", loginMember);
        model.addAttribute("items", items);
        model.addAttribute("menu1", "product");
        model.addAttribute("menu2", category);

        ItemForm searchForm = new ItemForm();
        searchForm.setCategory1(category);
        if(category2 != null && !category2.isEmpty()) {
            searchForm.setCategory2(category2);
        }else {
            searchForm.setCategory2(null);
        }
        if(name != null && !name.isEmpty()) searchForm.setName(name);
        model.addAttribute("searchForm", searchForm);

        return "admin/product/productList";
    }

    //제품등록 화면 조회
    @GetMapping("/admin/items/{category}/new")
    public String createItemForm(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model,  @PathVariable String category) {
        //세션에 회원데이터가 없으면 제품소개 페이지로 이동
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        model.addAttribute("member", loginMember);
        model.addAttribute("form", new Item());
        model.addAttribute("menu1", "product");
        model.addAttribute("menu2", category);
        return "admin/product/createForm";
    }

    //제품등록
    @PostMapping("/admin/items/new")
    public String create(ItemForm form) throws IOException {
        itemService.save(form);
        return "redirect:/admin/items/"+ form.getCategory1();
    }

    //제품수정 화면 조회
    @GetMapping("/admin/items/{itemId}/edit")
    public String updateItemForm(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, @PathVariable("itemId") Long itemId, Model model) {
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        model.addAttribute("member", loginMember);

        Item item = itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setContent(item.getContent());
        form.setCategory1(item.getCategory1());
        form.setCategory2(item.getCategory2());
        form.setFileName(item.getFileName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form", form);
        model.addAttribute("menu1", "product");
        model.addAttribute("menu2", item.getCategory1());
        return "admin/product/updateForm";
    }

    //제품수정
    @PostMapping("/admin/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") ItemForm form) throws IOException {
        itemService.updateItem(form);
        return "redirect:/admin/items/" + form.getCategory1();
    }

    //제품삭제
    @PostMapping("/admin/items/{itemId}/delete")
    public String deleteItem(@ModelAttribute("form") ItemForm form) throws IOException {
        String category = itemService.deleteItem(form);
        return "redirect:/admin/items/" + category;
    }

    //게시글 조회
    @GetMapping("/admin/board/{type}")
    public String boards(@PathVariable String type, Model model) {
        List<Board> boards = boardService.findByType(type);
        model.addAttribute("boards", boards);
        model.addAttribute("type", type);

        return "home";
    }

    //문의하기
    @GetMapping("/admin/contacts")
    public String contacts(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model ) {
        log.info("banner controller");
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        model.addAttribute("member", loginMember);

        return "home";
    }

}
