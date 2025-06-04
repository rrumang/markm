package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.Member;
import jpabarcode.jpashop.repository.MemberRepository;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final LoginService loginService;

    @RequestMapping("/")
    public String homeV3(@SessionAttribute(name = "LOGIN_MEMBER", required = false) Member loginMember, Model model ) {
        log.info("home controller");
        //세션에 회원데이터가 없으면 home
        if(loginMember == null) {
            model.addAttribute("member", new Member());
            return "home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "home";
    }

    @RequestMapping("/company")
    public String company() {
        log.info("company controller");
        return "company";
    }

    //관리자 기능
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("form") LoginForm form) {
        return "admin/loginForm";
    }

    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute("form") LoginForm form, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) return "admin/loginForm";

        Member loginMember = loginService.login(form.getEmail(), form.getPassword());
        if (loginMember == null) {
            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "admin/loginForm";
        }

        //로그인 성공
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute("LOGIN_MEMBER", loginMember);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
