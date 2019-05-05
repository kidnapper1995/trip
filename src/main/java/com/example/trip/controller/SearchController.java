package com.example.trip.controller;

import com.example.trip.bean.Blog;
import com.example.trip.service.BlogDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class SearchController {
    @Autowired
    BlogDataService blogDataService;
    @RequestMapping("/search")
    public String search(Model model,String searchContent){
        List<Blog> results=blogDataService.search(searchContent);
        model.addAttribute("results",results);
        return "/user/search";
    }
}
