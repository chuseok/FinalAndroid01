package org.dragon.android.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.login.domain.AuthVO;
import org.login.domain.MemberVO;
import org.login.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping(value = "/signup")
	public Map<String, String> register(HttpServletRequest request) {
		
		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userEmail = request.getParameter("userEmail");
		String userPhone = request.getParameter("userPhone");
		String userBirth = request.getParameter("userBirth");
		
		MemberVO member = new MemberVO();
		member.setUserName(userName);
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		member.setEmail(userEmail);
		member.setPhoneNo(userPhone);
		member.setBirthday(userBirth);
		
		AuthVO auth = new AuthVO();
		auth.setAuth("ROLE_MEMBER");
		auth.setUserId(userId);
		
		System.out.println(member.getUserName());
		Map<String, String> map = new HashMap<String, String>();

		int insertMemCount = memberService.register(member);
		int insertAuthCount = memberService.register(auth);
		
		if(insertMemCount == 1 && insertAuthCount == 1) {
			map.put("insertResult", "success");
		} else {
			map.put("insertResult", "error");
		}
		
		return map;
		
	}
	
}
