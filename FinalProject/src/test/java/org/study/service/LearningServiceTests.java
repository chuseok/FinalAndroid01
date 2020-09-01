package org.study.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.study.service.LearningService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class LearningServiceTests {

	@Setter(onMethod_ = {@Autowired})
	private LearningService learningservice;
	
	
	@Test
	public void testgetAllWoreJsonList() {
		
		log.info(learningservice.readAllJson());
	}
	
	@Test
	public void testgetAllWoreList() {
		
		log.info(learningservice.getAllWordList());
	}
	
	
	@Test
	public void testGetWordDTO() {
		log.info(learningservice.getWordDTO("hansol", "단어장"));
		
	}
	
	@Test
	public void testgetWordList() {
				
		log.info(learningservice.getWordJsonArray("hansol", "단어장"));
		
	}
	
	@Test
	public void testUpRate() {
				
		learningservice.upRate("hansol", "단어장","dog");
		
	}
	
	
	
	
}
