package jpabarcode.jpashop.service;

import jpabarcode.jpashop.domain.item.Item;
import jpabarcode.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
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
