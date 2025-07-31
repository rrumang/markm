package jpabarcode.jpashop.repository;

import jpabarcode.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    //상품 삭제
    public void deleteOne(Long id) {
        Item item = em.find(Item.class, id);
        if (item != null) {
            em.remove(item);
        }
    }

    //상품 목록조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    //상품 카테고리별 목록조회
    public List<Item> findByCategory(String category1, String category2, String name) {
        String jpql = "select i from Item i where i.category1 = :category1";
        if(category2 != null && !category2.isEmpty()) jpql += " and i.category2 = :category2";
        if(name != null) jpql += " and i.name like :name";

        TypedQuery<Item> query = em.createQuery(jpql, Item.class);
        query.setParameter("category1",  category1);
        if(category2 != null && !category2.isEmpty()) query.setParameter("category2", category2);
        if(name != null) query.setParameter("name", "%" + name + "%");

        return query.getResultList();
    }
}
