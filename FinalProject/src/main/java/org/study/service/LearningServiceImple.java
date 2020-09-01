package org.study.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.study.domain.WordDTO;
import org.study.domain.WordVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Log4j
@AllArgsConstructor
public class LearningServiceImple implements LearningService {

	@Override
	public JSONArray readAllJson() {

		log.info("read JsonFile.....");

		JSONArray wordArray = new JSONArray();
		JSONArray itemArray = new JSONArray();

		JSONObject wordObj;

		try {

			List<WordDTO> jsonDTOList = new ObjectMapper().readValue(new File("C:/temp/test01.json"),
					new TypeReference<List<WordDTO>>() {
					});

			int size = jsonDTOList.size();

			for (int i = 0; i < size; i++) {

				WordDTO str = jsonDTOList.get(i);

				/*
				 * log.info("id : " + str.getId()); log.info("title : " + str.getTitle());
				 * log.info("item : " + str.getItem());
				 */

				wordObj = new JSONObject();

				wordObj.put("id", str.getId());
				wordObj.put("title", str.getTitle());

				for (WordVO item : str.getItem()) {
					JSONObject itemObj = new JSONObject();
					itemObj.put("word", item.getWord());
					itemObj.put("meaning", item.getMeaning());
					itemObj.put("learningRate", item.getLearningRate());
					itemArray.add(itemObj);
				}

				wordObj.put("item", itemArray);
				wordArray.add(wordObj);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return wordArray;
	}

	@Override
	public List<WordDTO> getAllWordList() {
		List<WordDTO> jsonDTOList = null;
		try {
			jsonDTOList = new ObjectMapper().readValue(new File("C:/temp/test01.json"),
					new TypeReference<List<WordDTO>>() {
					});

		} catch (IOException e) {

			e.printStackTrace();
		}
		jsonDTOList.forEach(System.out::println);
		return jsonDTOList;
	}

	@Override
	public JSONArray getWordJsonArray(String id, String title) {
		// log.info("get wordList.....");

		JSONArray wordArray = new JSONArray();
		JSONArray itemArray = new JSONArray();

		JSONObject wordObj;

		try {
			List<WordDTO> jsonDTOList = new ObjectMapper().readValue(new File("C:/temp/test01.json"),
					new TypeReference<List<WordDTO>>() {
					});
			int size = jsonDTOList.size();
			for (int i = 0; i < size; i++) {
				WordDTO str = jsonDTOList.get(i);

				if (str.getId().equals(id) && str.getTitle().equals(title)) {
					wordObj = new JSONObject();

					wordObj.put("id", str.getId());
					wordObj.put("title", str.getTitle());

					for (WordVO item : str.getItem()) {
						JSONObject itemObj = new JSONObject();
						itemObj.put("word", item.getWord());
						itemObj.put("meaning", item.getMeaning());
						itemObj.put("learningRate", item.getLearningRate());
						itemArray.add(itemObj);
					}

					wordObj.put("item", itemArray);
					wordArray.add(wordObj);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return wordArray;
	}

	@Override
	public WordDTO getWordDTO(String id, String title) {
		List<WordDTO> worddto = getAllWordList();

		return worddto.stream().filter(w -> w.getId().equals(id) && w.getTitle().equals(title)).findFirst().get();
	}

	@Override
	public void upRate(String id, String title, String word) {
		WordDTO dto = getWordDTO(id, title);
		for (WordVO item : dto.getItem()) {
			if (item.getWord().equals(word)) {
				int rate = item.getLearningRate();
				log.info("바뀌기 전 : "+rate);
				if(rate == 0) {
					item.setLearningRate(1);
				}
				else {
					rate += 1;
					item.setLearningRate(rate);
				}
				log.info("바뀐 후 : "+rate);
				//item.setLearningRate(1);
				log.info(dto.getId()+','+dto.getTitle());
				log.info(item.getWord()+','+item.getMeaning()+','+item.getLearningRate());
			}
		}				
		  JSONArray wordArray = new JSONArray(); 
		  JSONArray itemArray = new JSONArray();
		  
		  JSONObject wordObj;
		  
		  
		  try { List<WordDTO> jsonDTOList = new ObjectMapper().readValue(new
		  File("C:/temp/test01.json"), new TypeReference<List<WordDTO>>() {}); 
		  
		  int size = jsonDTOList.size(); 
		  
		  for (int i = 0; i < size; i++) 
		  { 
			  WordDTO str = jsonDTOList.get(i);
			  
			  //바뀐데이터로 저장
			  if(str.getId().equals(dto.getId()) && str.getTitle().equals(dto.getTitle()))
			  { 
				  str.setItem(dto.getItem());		  
			  } 
					
			  wordObj = new JSONObject(); 
			  wordObj.put("id", str.getId());
			  wordObj.put("title", str.getTitle()); 
			  
			  for (WordVO item : str.getItem()) 
			  { 
				  JSONObject itemObj = new JSONObject();
				  itemObj.put("word", item.getWord()); 
				  itemObj.put("meaning", item.getMeaning());
				  itemObj.put("learningRate", item.getLearningRate());
				  itemArray.add(itemObj); 
			  }			
			  
			  wordObj.put("item",str.getItem());
			  wordArray.add(wordObj);
			  
		 }		  
		  log.info(wordArray);	  
		
		  //file로 저장
		  BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(new
		  FileOutputStream("C:\\temp\\test01.json"), "utf-8"));
		  writer.write(wordArray.toString()); 
		  writer.flush(); 
		  writer.close();	  
		  
		  } catch (IOException e1){ 
			  e1.printStackTrace(); 
		  }
		  
	}//end upRate
	
	
	@Override
	public void resetRate(String id, String title) {
				
		  JSONArray wordArray = new JSONArray(); 
		  JSONArray itemArray = new JSONArray();
		  
		  JSONObject wordObj;		  
		  
		  try { List<WordDTO> jsonDTOList = new ObjectMapper().readValue(new
		  File("C:/temp/test01.json"), new TypeReference<List<WordDTO>>() {}); 
		  
		  int size = jsonDTOList.size(); 
		  
		  for (int i = 0; i < size; i++) 
		  { 
			  WordDTO str = jsonDTOList.get(i);
								
			  wordObj = new JSONObject(); 
			  wordObj.put("id", str.getId());
			  wordObj.put("title", str.getTitle()); 
			  
			  if(str.getId().equals(id)&&str.getTitle().equals(title)) {
				  for (WordVO item : str.getItem()) 
				  { 
					  JSONObject itemObj = new JSONObject();
					  itemObj.put("word", item.getWord()); 
					  itemObj.put("meaning", item.getMeaning());
					  itemObj.put("learningRate", 0);
					  itemArray.add(itemObj); 
				  }	
				  wordObj.put("item",itemArray);
			  }
			  else {
				  wordObj.put("item",str.getItem());
			  }		
			  
			  wordArray.add(wordObj);
			  
		 }		  
		  log.info(wordArray);	  
		
		  //file로 저장
		  BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(new
		  FileOutputStream("C:\\temp\\test01.json"), "utf-8"));
		  writer.write(wordArray.toString()); 
		  writer.flush(); 
		  writer.close();	  
		  
		  } catch (IOException e1){ 
			  e1.printStackTrace(); 
		  }
		
	}



}
