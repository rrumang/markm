package jpabarcode.jpashop.service;

import jpabarcode.jpashop.controller.ItemForm;
import jpabarcode.jpashop.domain.UploadFile;
import jpabarcode.jpashop.domain.item.Item;
import jpabarcode.jpashop.file.FileStore;
import jpabarcode.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @Transactional
    public void save(ItemForm form) throws IOException {
        Item item = new Item();
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());

        if (!form.getAttachFile().isEmpty()) {
            //파일저장
            MultipartFile attachFile = form.getAttachFile();
            UploadFile uploadFile = fileStore.storeFile(attachFile);
            //파일명 추가
            item.setFileName(uploadFile.getStoreFileName());
        }
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(ItemForm form) throws IOException {
        Item findItem = itemRepository.findOne(form.getId());
        findItem.setName(form.getName());
        findItem.setPrice(form.getPrice());
        findItem.setStockQuantity(form.getStockQuantity());

        if (!form.getAttachFile().isEmpty()) {
            //파일저장
            MultipartFile attachFile = form.getAttachFile();
            UploadFile uploadFile = fileStore.storeFile(attachFile);
            //기존파일 삭제
            fileStore.deleteFile(findItem.getFileName());
            //파일명 추가
            findItem.setFileName(uploadFile.getStoreFileName());
        }
    }

    //상품 목록조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    //상품 단건조회
    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

}
