package com.example.trip.dao;

import com.example.trip.bean.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentDao {

    @Select(value = "select * from comment where id=#{id}")
    public Comment findComments(Integer id);

    @Select(value = "select * from comment where date=#{date} and content=#{content}")
    public Comment findCommentsByDateAndContent(String date,String content);

    @Select(value = "SELECT * from comment where pid=#{pid}")
    public List<Comment> findSonComment(Integer pid);

    @Insert(value = "Insert comment (user,replyUser,date,content,pid) values (#{user}," +
            "#{replyUser},#{date},#{content},#{pid})")
    public boolean addSonComment(String user,String replyUser,String date,String content,int pid);
}
