package jpabarcode.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrinterForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
