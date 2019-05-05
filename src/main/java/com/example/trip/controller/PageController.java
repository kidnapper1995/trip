package com.example.trip.controller;

import com.example.trip.bean.SUser;
import com.example.trip.service.security.SecurityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 请求页面分发，注意和WebMvcConfig的对比，功能类似
 * @author Veiking
 */
@Controller
public class PageController {
	@Autowired
	SecurityDataService securityDataService;



	
	@RequestMapping("/admin")
	@PreAuthorize("hasAuthority('R_ADMIN')")
	public String admin(Model model, String tt) {
		return "admin";
	}

	@RequestMapping("/index")
	public String index(Model model, String tt) {
		return "index";
	}

	@RequestMapping("/error")
	public String error(Model model, String tt) {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Model model, String tt) {
		return "login";
	}

	@RequestMapping("/register")
	public String re(Model m) {
		return "register";
	}

	@RequestMapping("/register/forget")
	public String forget(){
		return "register/forget";
	}

	@RequestMapping("/register/getPassword")
	public String getPassword(Model model,String email){
		SUser sUser=securityDataService.findSUserByEmail(email);
		System.out.println(sUser);
		if (sUser==null){
			model.addAttribute("error","没有此邮箱，请确认邮箱地址!三秒钟自动跳转注册页面");
			return "register/error";
		}
		else {
			model.addAttribute("error","您的密码是："+sUser.getPassword()+" 请尽快确认！此页面5秒后跳转");
			return "register/getPasswordSuccess";
		}
	}

	@RequestMapping("/register/user")
	public String register(Model model, String userName, String password,String email) {
		SUser sUser1=securityDataService.findSUserByName(userName);
		SUser sUser2=securityDataService.findSUserByEmail(email);
		if (sUser1!=null){
			model.addAttribute("error","用户名已被占用，请重新注册!三秒钟自动跳转注册页面");
			return "register/error";
		}
		if (sUser2!=null){
			model.addAttribute("error","邮箱已被占用，请重新注册!三秒钟自动跳转注册页面");
			return "register/error";
		}


			securityDataService.addUser(userName,email,password);
			model.addAttribute("error","注册成功！三秒钟自动跳转登陆页面");
			return "register/success";



	}





}
