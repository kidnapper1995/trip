package com.example.trip.dao;

import com.example.trip.bean.Blog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogDao {
    @Select(value = "select * from blog where userName=#{userName}")
    public List<Blog> findBlogsByUserName(String userName);

    @Select(value = "select * from blog where userName=#{userName} and date=#{date}")
    public Blog findBlogByDateAndUserName(String userName,String date);

    @Select(value = "select * from blog where id=#{id}")
    public Blog findBlogById(Integer id);

    @Insert(value = "INSERT blog (title,userName,date,content) values (#{title}" +
            ",#{userName},#{date},#{content})")
    public boolean addBlog(String title,String userName,String date,String content);

    @Select(value = "select * from blog where title like '%${searchContent}%' ")
    public List<Blog> findSearchedBlogs(@Param("searchContent") String searchContent);

    @Delete(value = "DELETE FROM blog where userName=#{userName} and date=#{date}")
    public void deleteBlog(String userName,String date);

    @Update(value = "UPDATE blog SET commentList = #{newContent} WHERE (id = #{id})")
    public void updateComment(String newContent,Integer id);
}
