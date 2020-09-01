package org.study.controller;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.study.service.LearningService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;

@Controller
@Log4j
@RequestMapping("/learn/*")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class LearningController {
	
	private LearningService learningservice;
	
	@GetMapping("/read")
	public JSONArray getAllWordDTO() {
		
		log.info("get all list....");
		log.info(learningservice.readAllJson());
		return learningservice.readAllJson();
	}
	
	
	@GetMapping("/list")
	public void list(Model model) {
		
		log.info("get all list........");
		
		model.addAttribute("list", learningservice.getAllWordList());
	}
	
	@GetMapping("/get")
	public void get(@RequestParam("id") String id, @RequestParam("title") String title, Model model) {
		
		log.info("get word List..");
		
		model.addAttribute("WordDTO", learningservice.getWordJsonArray(id, title));		
		
	}
	
	@GetMapping("/subjective")
	public void moveSubjective(@RequestParam("id") String id, @RequestParam("title") String title, Model model) {
		
		log.info("get Array " + title + " for subjective quiz.....");
		
		model.addAttribute("WordDTO", learningservice.getWordJsonArray(id, title));
	}
	
	@GetMapping("/study")
	public void moveStudy(@RequestParam("id") String id, @RequestParam("title") String title, Model model) {
		
		log.info("get Array " + title + " for study.....");
		
		model.addAttribute("WordDTO", learningservice.getWordJsonArray(id, title));
	}
	
	@GetMapping("/test")
	public void moveTest(@RequestParam("id") String id, @RequestParam("title") String title, Model model) {
		
		log.info("get Array " + title + " for test.....");
		
		model.addAttribute("WordDTO", learningservice.getWordJsonArray(id, title));
	}
	
	
	
	
	
	
	
	
	

}
