package com.shangde.gao.config;

import com.shangde.gao.domain.BookBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ConfigurationProperties(prefix = "book")
@PropertySource("classpath:book.properties")
public class MyConfigInfo {

    private String name;
    private String author;
    private String price;

    @Bean
    public BookBean bookBean(){
        BookBean testBean = new BookBean();
        testBean.setName(name);
        testBean.setAuthor(author);
        testBean.setPrice(price);
        return testBean;
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