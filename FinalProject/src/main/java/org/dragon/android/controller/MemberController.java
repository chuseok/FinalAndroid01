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
	
	@GetMapping(value = "/get")
	public Map<String, String> get(HttpServletRequest request) {
		
//		String userId = request.getParameter("userId");
		String userId = "aaa";
		List<MemberVO> MemList = new ArrayList<MemberVO>();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        
		MemberVO member = memberService.get(userId);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", member.getUserId());
		
		return map;
		
	}
	
}
