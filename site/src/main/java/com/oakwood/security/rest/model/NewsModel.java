package com.oakwood.security.rest.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * [description]
 *
 * @author syeremy
 * @version 1.0
 * @date 3/3/17
 **/
@XmlRootElement(name = "news")
public class NewsModel {

    private String title;
    private Date date;
    private String introduction;
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
