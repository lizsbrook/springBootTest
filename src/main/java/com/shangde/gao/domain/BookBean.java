package com.shangde.gao.domain;


import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;


@Table(name = "test_book")
public class BookBean implements Serializable {

    public BookBean()
    {

    }
    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "price")
    private String price;

    @Override
    public String toString() {
        return "BookBean{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
