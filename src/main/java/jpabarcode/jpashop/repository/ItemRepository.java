package jpabarcode.jpashop.repository;

import jpabarcode.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        }else {
            em.merge(item);
        }
    }

    //상품 단건조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    //상품 목록조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
