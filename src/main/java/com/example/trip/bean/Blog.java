package com.example.trip.bean;

import java.util.Date;
import java.util.List;

public class Blog {
    private Integer id;
    private String title;
    private String date;
    private String userName;
    private String content=null;
    private String commentList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getCommentList() {
        return commentList;
    }

    public void setCommentList(String commentList) {
        this.commentList = commentList;
    }
}




