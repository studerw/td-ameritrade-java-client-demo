package com.studerw.tda.demo.mvc;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String home(Model model){
		model.addAttribute("title", "Hello World!");
		return "index";
	}
}