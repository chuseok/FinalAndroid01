package org.android.controller;

import org.dragon.service.DragonService;
import org.dragon.service.InventoryService;
import org.login.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/android/member")
@RestController
@Log4j
@AllArgsConstructor
public class MemberController {

	MemberService memberService;
	
	
	
}
