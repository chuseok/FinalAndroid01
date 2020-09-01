package org.dragon.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.dragon.domain.DragonVO;
import org.dragon.domain.RankVO;
import org.dragon.service.DragonService;
import org.dragon.service.MainService;
import org.dragon.service.RankService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/dragon/*")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class RankingController {

	private RankService service;
	private MainService mainService;
	private DragonService dragonService;
	@GetMapping("/ranking")
	public void ranking(Principal principal, Model model) {
		if (principal == null) {
			model.addAttribute("alert", true);
			return;
		}
		String userId = principal.getName();
		
		long lastDate = mainService.getDate(userId).getTime();
		model.addAttribute("connectionTime", lastDate);
		
		
		DragonVO vo = dragonService.getDragonByUser(userId);
		if(vo==null) {
			model.addAttribute("profile", -1);
		}else {
			List<RankVO> rankingList = service.getRankList();
			RankVO userInfo = service.getUserRank(userId);
			model.addAttribute("rank", rankingList);
			model.addAttribute("userInfo", userInfo);
			model.addAttribute("profile", userInfo.getImg());
			
			
		}
		
	}
}
