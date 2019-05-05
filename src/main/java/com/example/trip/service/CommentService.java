package com.example.trip.service;

import com.example.trip.bean.Blog;
import com.example.trip.bean.Comment;
import com.example.trip.dao.BlogDao;
import com.example.trip.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    BlogDao blogDao;
    @Autowired
    CommentDao commentDao;


    public Comment findParentComment(Integer id){

        return commentDao.findComments(id);
    }

    public List<Integer> findParentCommentList(Blog blog){
        if (blog.getCommentList()!=null){
            String[] comments=blog.getCommentList().split("\\+");
            ArrayList<Integer> parentCommentList=new ArrayList<>();
            for (int i=0;i<comments.length;i++){
                parentCommentList.add(Integer.valueOf(comments[i]));
            }
            return parentCommentList;
        }
        return null;

    }

    public List<Comment> findSonCommentList(Integer pid){
        return commentDao.findSonComment(pid);
    }

    public void addSonComment(String user,String content,int pid,String dateString){
        String replyUser=null;

        replyUser=commentDao.findComments(pid).getUser();


        commentDao.addSonComment(user,replyUser,dateString,content,pid);

    }

    public void addParentComment(String user,String content,int pid,String date,Integer blogId){
        commentDao.addSonComment(user,null,date,content,pid);
        Comment comment=commentDao.findCommentsByDateAndContent(date,content);
        Blog blog=blogDao.findBlogById(blogId);
        String commentList="";
        commentList=commentList+blog.getCommentList();
        commentList=commentList+"+"+comment.getId();
        blogDao.updateComment(commentList,blogId);


    }


}
