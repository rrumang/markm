package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.Board;
import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 작성
    @GetMapping("/board/{type}/new")
    public String createBoard(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, @PathVariable String type, Model model) {
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        Board board = new Board();
        board.setType(type);

        model.addAttribute("form", board);
        return "board/createBoardForm";
    }

    //게시글 등록
    @PostMapping("/board/new")
    public String create(BoardForm form) throws IOException {
        boardService.save(form);
        return "redirect:/";
    }

    //게시글 조회
    @GetMapping("/board/{type}")
    public String boards(@PathVariable String type, Model model) {
        List<Board> boards = boardService.findByType(type);
        model.addAttribute("boards", boards);
        model.addAttribute("type", type);

        return "board/boardList";
    }

    //게시글상세 조회
    @GetMapping("/board/{type}/{itemId}/view")
    public String itemView(@PathVariable("itemId") Long itemId, @PathVariable("type") String type, Model model) {
        Optional<Board> board = boardService.findOne(itemId, type);
        model.addAttribute("board", board);
        return "items/itemView";
    }

}
