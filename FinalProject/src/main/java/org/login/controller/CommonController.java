package org.login.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class CommonController {

	@GetMapping("/accessError")
	public void accessDenined(Authentication auth, Model model) {
		log.info("Access Denied : " + auth);
		
		model.addAttribute("msg", "Access Denied");
	}
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		log.info("error: " + error);
		log.info("logout: " + logout);
		
		if(error!=null) {
			model.addAttribute("error", "아이디 또는 비밀번호 오류입니다!");
		}
		if(logout!=null) {
			model.addAttribute("logout", "로그아웃 하였습니다!");
		}
	}
	
	@GetMapping("/customLogout")
	public void logoutGET() {
		log.info("custom logout");
	}
}
