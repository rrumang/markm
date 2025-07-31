package jpabarcode.jpashop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(nullable = false)
    private String type;

   private String writer;

    private String fileName;
}
