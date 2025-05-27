package jpabarcode.jpashop.controller;

import jpabarcode.jpashop.domain.item.Item;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class ItemForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private MultipartFile attachFile;
}
