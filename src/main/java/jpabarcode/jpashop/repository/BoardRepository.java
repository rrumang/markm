package jpabarcode.jpashop.repository;

import jpabarcode.jpashop.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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
    public Optional<Board> findOne(Long id, String type) {
        return em.createQuery("select m from Board m where m.type = :type and m.id = :id", Board.class)
                .setParameter("type", type)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    //게시판 타입별 목록조회
    public List<Board> findByType(String type) {
        return em.createQuery("select m from Board m where m.type = :type", Board.class)
                .setParameter("type", type).getResultList();
    }
}
