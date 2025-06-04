package jpabarcode.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "회원 이메일주소는 필수입니다.")
    private String email;

    @NotEmpty(message = "회원 비밀번호는 필수입니다.")
    private String password;

    private String city;
    private String street;
    private String zipcode;
}
