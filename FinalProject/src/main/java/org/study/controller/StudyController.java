package org.study.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.study.domain.WordDTO;
import org.study.service.LearningService;

import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@PreAuthorize("isAuthenticated()")
public class StudyController {
	
	@Autowired
	private LearningService learningservice;
	
	@RequestMapping("/study")
	public List<WordDTO> getAllWordList(){
		
		log.info("get all list........");

		return learningservice.getAllWordList();		
	}
	
	@RequestMapping("/study/{id}/{title}")
	public WordDTO getWordDTO(@PathVariable String id, @PathVariable String title ) {
		return learningservice.getWordDTO(id,title);
	}	
	
	@RequestMapping(method = RequestMethod.POST,
			value = "/study/uprate",
			consumes="application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE })
	public void upRate(@RequestBody Map<String, Object> param ) {
		String id = param.get("id").toString();
		String title = param.get("title").toString();
		String word = param.get("word").toString();
		learningservice.upRate(id,title,word);
	}
	
	
	@RequestMapping(method = RequestMethod.POST,
			value = "/study/resetrate",
			consumes="application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE })
	public String resetRate(@RequestBody Map<String, Object> param/* , RedirectAttributes rttr */ ) throws Exception {
		String id = param.get("id").toString();
		String title = param.get("title").toString();
		log.info(id+","+title+"initialized.....");
		
		learningservice.resetRate(id,title);
		/*
		 * rttr.addAttribute("id",id); rttr.addAttribute("title",title);
		 */
		
		return "success";
				
	}
	

}
