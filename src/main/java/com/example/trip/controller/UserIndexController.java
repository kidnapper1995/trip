package com.example.trip.controller;

import com.example.trip.bean.Blog;
import com.example.trip.service.BlogDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserIndexController {
    @Autowired
    BlogDataService blogDataService;

    @RequestMapping("/navigation")
    public String navigation(Model model){
        return "/user/navigation";
    }


    @RequestMapping("/index")
    public String index(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        HashMap<String,String> result=blogDataService.findBlogsByUserName(userDetails.getUsername());
        model.addAttribute("blog",result);
        return "user/index";
    }

}
