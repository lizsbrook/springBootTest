package com.yuning.gao.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "test_book")
public class BookBean extends MetaEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private String price;

}
