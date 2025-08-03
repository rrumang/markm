package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.Board;
import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.service.BoardService;
import jpabarcode.jpashop.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;
    private final BoardService boardService;

    @RequestMapping("/")
    public String homeV3(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model ) {
        //세션에 회원데이터가 없으면 home
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        // 배너이미지 조회
        List<Board> banners = boardService.findByType("banner");
        model.addAttribute("banners", banners);

        // 공지사항 5개불러오기
        List<Board> notices = boardService.findByTypeLimit("notice", 5);
        model.addAttribute("notices", notices);

        return "home";
    }

    // 로그인화면 이동
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") LoginForm form) {
        return "admin/signin";
    }

    // 로그인
    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute("form") LoginForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) return "admin/signin";

        Member loginMember = loginService.login(form.getEmail(), form.getPassword());
        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "admin/signin";
        }

        //로그인 성공
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute("LOGIN_MEMBER", loginMember);

        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // 회사소개 - CEO 인사말
    @RequestMapping("/company/about")
    public String about(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model) {
        log.info("about controller");

        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        Board about = boardService.findByType("about").get(0);
        model.addAttribute("about", about);
        return "company/about";
    }

    // 회사소개 - 찾아오시는길
    @RequestMapping("/company/about2")
    public String about2(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model) {
        log.info("about2 controller");

        //세션 회원데이터
        model.addAttribute("member", Objects.requireNonNullElseGet(loginMember, Member::new));

        Board about2 = boardService.findByType("about2").get(0);
        model.addAttribute("about2", about2);
        return "company/about2";
    }

}
