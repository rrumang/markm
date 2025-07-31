package jpabarcode.jpashop.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class BoardForm {
    private Long id;

    @NotEmpty(message = "제목은 필수입니다.")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "내용은 필수입니다.")
    @Column(nullable = false)
    private String content;

    @NotEmpty(message = "게시판 타입은 필수입니다.")
    @Column(nullable = false)
    private String type;

    private String fileName;

    private MultipartFile attachFile;

    //배너
    private Long id1;
    private Long id2;
    private Long id3;
    private Long id4;
    private Long id5;

    private String title1 = "";
    private String title2 = "";
    private String title3 = "";
    private String title4 = "";
    private String title5 = "";

    private String content1 = "";
    private String content2 = "";
    private String content3 = "";
    private String content4 = "";
    private String content5 = "";

    private String fileName1;
    private String fileName2;
    private String fileName3;
    private String fileName4;
    private String fileName5;

    private MultipartFile attachFile1;
    private MultipartFile attachFile2;
    private MultipartFile attachFile3;
    private MultipartFile attachFile4;
    private MultipartFile attachFile5;

}
