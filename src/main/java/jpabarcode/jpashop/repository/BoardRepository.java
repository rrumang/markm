package jpabarcode.jpashop.repository;

import jpabarcode.jpashop.domain.Board;
import jpabarcode.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        if (board.getId() == null) {
            em.persist(board);
        }else {
            em.merge(board);
        }
    }

    //게시글 단건조회
    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    //게시글 삭제
    public void deleteOne(Long id) {
        Board board = em.find(Board.class, id);
        if (board != null) {
            em.remove(board);
        }
    }

    //게시판 타입별 목록조회
    public List<Board> findByType(String type) {
        return em.createQuery("select m from Board m where m.type = :type", Board.class)
                .setParameter("type", type).getResultList();
    }

    //게시판 타입별 목록조회
    public List<Board> findByType2(String type, String title, int startIndex, int pageSize) {
        String jpql = "select m from Board m where m.type = :type";
        if (title != null && !title.isEmpty()) jpql += " and m.title like :title";
        jpql += " order by m.id desc";

        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        query.setParameter("type", type);
        if (title != null && !title.isEmpty()) query.setParameter("title", "%" + title + "%");

        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    //게시판 타입, 제목별 목록갯수
    public long findByTypeCnt(String type, String title) {
        String jpql = "select count(*) from Board m where m.type = :type";
        if (title != null && !title.isEmpty()) jpql += " and m.title like :title";

        Query query = em.createQuery(jpql);
        query.setParameter("type", type);
        if (title != null && !title.isEmpty()) query.setParameter("title", "%" + title + "%");

        return (long) query.getSingleResult();
    }

    //게시판 타입별 목록조회
    public List<Board> findByTypeLimit(String type, int limit) {
        return em.createQuery("select m from Board m where m.type = :type order by m.id desc", Board.class)
                .setParameter("type", type)
                .setMaxResults(limit).getResultList();
    }
}
