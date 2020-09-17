package org.dragon.android.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.login.domain.MemberVO;
import org.login.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/android/member")
@RestController("android.controller.MemberController")
@Log4j
@AllArgsConstructor
public class MemberController {

	MemberService memberService;
	
	@GetMapping(value = "/getList")
	public List<Map<String, String>> get(HttpServletRequest request) {
        List<MemberVO> MemList = new ArrayList<MemberVO>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        
        MemList = memberService.getList();
		
        
        for(int i=0;i<MemList.size();i++) {
        	Map<String, String> map = new HashMap<String, String>();
        	String userId = MemList.get(i).getUserId();
        	String userName = MemList.get(i).getUserName();
        	map.put("userId", userId);
        	map.put("userName", userName);
        	
        	result.add(map);
        }
		
		return result;
		
	}
	
}
