package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.Board;
import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.domain.Pagination;
import jpabarcode.jpashop.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 조회
    @GetMapping("/board/{type}")
    public String boards(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, @PathVariable String type, Model model, @RequestParam(value = "title", required = false) String title, @RequestParam(defaultValue = "1") int page) {
        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        // 검색 조건
        BoardForm searchForm = new BoardForm();
        if(title != null && !title.isEmpty()) searchForm.setTitle(title);
        model.addAttribute("searchForm", searchForm);

        // 페이징 처리
        // 총 게시물 수
        long totalListCnt = boardService.findByTypeCnt(type, title);
        // 생성인자로 총 게시물 수, 현재 페이지를 전달
        Pagination pagination = new Pagination(totalListCnt, page);
        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        //타입별 게시판 조회
        List<Board> boards = boardService.findByType2(type, title, startIndex, pageSize);
        model.addAttribute("boards", boards);
        model.addAttribute("type", type);
        model.addAttribute("pagination", pagination);

        return "board/boardList";
    }

    //게시글상세 조회
    @GetMapping("/board/{boardId}/view")
    public String itemView(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, @PathVariable("boardId") Long boardId, Model model) {
        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);
        model.addAttribute("type", board.getType());

        return "board/boardDetail";
    }

}
