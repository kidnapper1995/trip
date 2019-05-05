package com.example.trip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

@Controller
public class AnnouncementController {
    public static LinkedList<String> announcementList=new LinkedList<String>(){
        @Override
        public String toString() {
            Iterator<String> it = iterator();
            if (! it.hasNext())
                return "No announcement!";

            StringBuilder sb = new StringBuilder();
            sb.append("Announcement:\n[");
            for (;;) {
                String e = it.next();
                sb.append(e);
                if (! it.hasNext())
                    return sb.append(']').toString();
                sb.append('\n').append(' ');
            }
        }
    };

    @RequestMapping("/user/announcement")
    public String announcementUser(Model m){
        m.addAttribute("announcement",announcementList);
        return "user/announcement";

    }

    @RequestMapping("/admin/announcement")
    public String announcementAdmin(Model m){
        m.addAttribute("announcement",announcementList);
        return "admin/announcement";

    }



    @RequestMapping("admin/announcement/toAdd")
    public String toAdd() {
        return "admin/announcement/toAdd";
    }

    @RequestMapping("/add")
    public String add(String newAnnouncement) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        announcementList.add(dateString+"      "+newAnnouncement);
        return "redirect:/admin/announcement";
    }

    @RequestMapping("/admin/announcement/toEdit")
    public String toEdit(Model model, String id) {
        model.addAttribute("index",announcementList.indexOf(id));
        model.addAttribute("content",id);
        return "admin/announcement/toEdit";
    }

    @RequestMapping("/edit")
    public String edit(String newContent,String index) {
        announcementList.set(Integer.valueOf(index),newContent);
        return "redirect:/admin/announcement";
    }




    @RequestMapping("admin/announcement/delete")
    public String delete(String id) {
        announcementList.remove(id);
        return "redirect:/admin/announcement";
    }

}
