package com.wp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wp.entity.Transporter;
import com.wp.model.LoginModel;
import com.wp.service.CustomerService;
import com.wp.service.TransporterService;

@Controller
//@SessionAttributes({"username"})
public class HomeController {

	@Autowired
	JavaMailSender mail;

	@Autowired
	CustomerService customerService;

	@Autowired
	TransporterService transporterService;

	@RequestMapping("/")
	public ModelAndView homePage(HttpSession session) {
		
		ModelAndView mv = null;
		// if already logged in then direct to respective home page
		if (session != null) {
			String userType = (String) session.getAttribute("utype");
			if (userType != null) {
				if (userType.equals("admin")) {
					mv = new ModelAndView("redirect:/admin/");
					return mv;
				} else if (userType.equals("customer")) {
					mv = new ModelAndView("redirect:/customer/");
					return mv;
				}

				else if (userType.equals("transporter")) {
					mv = new ModelAndView("redirect:/transporter/");
					return mv;
				}
			}
		}
		mv = new ModelAndView("index");
		mv.addObject("login", new LoginModel());
		return mv;		
	}

	@RequestMapping("login")
	public String login(@Valid @ModelAttribute("login") LoginModel loginModel,BindingResult bindingResult, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		if(bindingResult.hasErrors()) {
			System.out.println("error detected");
			return "index";
		}
		String username = loginModel.getUsername().trim();
		String password = loginModel.getPassword().trim();
		String userType = loginModel.getUserType();

		HttpSession session = request.getSession();

		ModelAndView mv = new ModelAndView("redirect:/");
		if (username.equals("admin") && password.equals("ssi")) {
			session.setAttribute("username", username);
			session.setAttribute("utype", "admin");

			return "redirect:/admin/";
		}

		else if (userType.equals("customer")) {
			if (customerService.verifyCustomer(username, password)) {
				System.out.println("success login");
				session.setAttribute("username", username);
				session.setAttribute("utype", userType);

				return "redirect:/customer/";

			} else
				System.out.println("wrong username and password");
		} else {
			if (transporterService.verifyTransporter(username, password)) {
				System.out.println("success login");
				Transporter transporter = transporterService.searchTransporter(username);

				session.setAttribute("username", username);
				session.setAttribute("cname", transporter.getCompanyName());
				session.setAttribute("utype", userType);
				return "redirect:/transporter/";

			} else
				System.out.println("wrong transporter username and password");
		}
		return "redirect:/";

	}


}
