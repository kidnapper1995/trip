package com.example.trip.controller;

import com.example.trip.bean.Blog;
import com.example.trip.bean.Comment;
import com.example.trip.service.BlogDataService;
import com.example.trip.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/user/blog")
public class BlogController {
    @Autowired
    BlogDataService blogDataService;
    @Autowired
    CommentService commentService;

    @RequestMapping("/show")
    public String show(Model model, String date, String userName){
        Blog blog=null;

        HashMap<Comment,List<Comment>> commentList=new HashMap<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (userName==null) {
            blog = blogDataService.findBlogByDateAndUserName(userDetails.getUsername(), date);
            userName = userDetails.getUsername();
        }
        else{
            blog=blogDataService.findBlogByDateAndUserName(userName,date);
            userName= userDetails.getUsername();
        }
        model.addAttribute("title",blog.getTitle());
        model.addAttribute("date",blog.getDate());
        model.addAttribute("userName",blog.getUserName());//作者
        model.addAttribute("blogId",blog.getId());
        model.addAttribute("user",userName);//目前的用户
        List<Integer> parentCommentList=commentService.findParentCommentList(blog);
        if(parentCommentList!=null)
        for (int i = 0; i < parentCommentList.size(); i++) {
            List<Comment> sonList=new ArrayList<>();
            int index=0;
            sonList.addAll(commentService.findSonCommentList(parentCommentList.get(i)));
            while (true){
                int size=sonList.size();
                if (index==size)
                    break;
                for (; index < size; index++) {
                    List<Comment> temp=commentService.findSonCommentList(sonList.get(index).getId());
                    if (temp!=null)
                        sonList.addAll(temp);
                }
            }

            commentList.put(commentService.findParentComment(parentCommentList.get(i)),
                    sonList);

        }
        model.addAttribute("commentList",commentList);


        String[] content=blogDataService.divideContent(blog.getContent());
        model.addAttribute("content",content);
        return "user/blog/show";
    }

    @RequestMapping("/show2")
    public String show2(Model model, @ModelAttribute(value="blogId")Integer blogId){
        Blog blog=null;

            blog=blogDataService.findBlogById(blogId);
            String date=blog.getDate();

        LinkedHashMap<Comment,List<Comment>> commentList=new LinkedHashMap<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String userName=userDetails.getUsername();
        model.addAttribute("title",blog.getTitle());
        model.addAttribute("date",date);
        model.addAttribute("userName",blog.getUserName());//作者
        model.addAttribute("blogId",blog.getId());
        model.addAttribute("user",userName);//目前的用户
        List<Integer> parentCommentList=commentService.findParentCommentList(blog);
        if(parentCommentList!=null)
            for (int i = 0; i < parentCommentList.size(); i++) {
                List<Comment> sonList=new ArrayList<>();
                int index=0;
                sonList.addAll(commentService.findSonCommentList(parentCommentList.get(i)));
                while (true){
                    int size=sonList.size();
                    if (index==size)
                        break;
                    for (; index < size; index++) {
                        List<Comment> temp=commentService.findSonCommentList(sonList.get(index).getId());
                        if (temp!=null)
                            sonList.addAll(temp);
                    }
                }

                commentList.put(commentService.findParentComment(parentCommentList.get(i)),
                        sonList);

            }
        model.addAttribute("commentList",commentList);


        String[] content=blogDataService.divideContent(blog.getContent());
        model.addAttribute("content",content);
        return "user/blog/show";
    }



    @RequestMapping("/post")
    public String post(Model model){
        return "user/blog/post";
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public String deleteBlog(Model model,String date){
        blogDataService.deleteBlog(date);
        return "redirect:/user/index";
    }

    @RequestMapping("/addBlog")
    public String addBlog(Model model,String content,String title){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        content=blogDataService.uploadImage(content);
        blogDataService.addBlog(title,userDetails.getUsername(),dateString,content);
        model.addAttribute("title",title);
        model.addAttribute("date",dateString);
        model.addAttribute("userName",userDetails.getUsername());
        String[] content1=blogDataService.divideContent(content);
        model.addAttribute("content",content1);
        return "user/blog/show";
    }


    @RequestMapping(value = "/comments",method = RequestMethod.POST)
    public String addComments(Model model, String commentContent, Integer blogId, String user, Integer pid, RedirectAttributes redirectAttributes){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        commentService.addSonComment(user,commentContent,pid,dateString);
        redirectAttributes.addAttribute("blogId",blogId);
        return "redirect:/user/blog/show2";
    }


    @RequestMapping(value = "/addParentComment" ,method = RequestMethod.POST)
    public String addParentComments(Model model, String parentComment, Integer blogid2, RedirectAttributes redirectAttributes){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        commentService.addParentComment(userDetails.getUsername(),parentComment,0,dateString,blogid2);
        redirectAttributes.addAttribute("blogId",blogid2);
        return "redirect:/user/blog/show2";
    }



}
