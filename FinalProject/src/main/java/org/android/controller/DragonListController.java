package org.android.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.dragon.controller.DragonController;
import org.dragon.controller.ProductController;
import org.dragon.domain.DragonVO;
import org.dragon.domain.ProductVO;
import org.dragon.service.DragonService;
import org.dragon.service.InventoryService;
import org.dragon.service.ProductService;
import org.login.domain.MemberVO;
import org.login.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/android")
@RestController
@Log4j
@AllArgsConstructor
public class DragonListController {

	DragonService service;
	MemberService memberService;
	InventoryService invenService;

	@GetMapping(value = "/dragon/main")
	public List<Map<String, String>> memberRequestAndResponse(HttpServletRequest request) {

		List<MemberVO> MemList = new ArrayList<MemberVO>();
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		MemList = memberService.getList();

		for (int i = 0; i < MemList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			String userId = MemList.get(i).getUserId();
			String userPwd = MemList.get(i).getUserPwd();
			String email = MemList.get(i).getEmail();
			String userName = MemList.get(i).getUserName();
			map.put("userId", userId);
			map.put("userPwd", userPwd);
			map.put("email", email);
			map.put("userName", userName);

			result.add(map);
		}

		return result;
	}

	@PostMapping(value = "/dragon/list")
	public List<Map<String, String>> dragonListWithRequestAndResponse(HttpServletRequest request) {

		String userId = request.getParameter("userId");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<DragonVO> dragonList = service.getAllDragonByUser(userId);
		
		for (int i = 0; i < dragonList.size(); i++) {
			DragonVO target = setImg(dragonList.get(i));
			Map<String, String> map = new HashMap<String, String>();
			map.put("dragonImage",target.getImg());
			map.put("hungryValue", target.getFoodValue()+"");
			map.put("dragonId",target.getDragonId()+"");
			
			result.add(map);
		}

		return result;
	}
	
	@GetMapping(value = "/dragon/get")
	public Map<String, String> getDragontWithRequestAndResponse(HttpServletRequest request) {

		String userId = request.getParameter("userId");
		int dragonId = Integer.parseInt(request.getParameter("dragonId"));
		Map<String, String> result = new HashMap<String, String>();
		DragonVO userInfo = new DragonVO(userId, dragonId, false);
		DragonVO dragon = service.getDragonByDragonId(userInfo);
		int coin = service.getCoin(userId);
		setImg(dragon);
		result.put("dragonImage",dragon.getImg());
		result.put("hungryValue", dragon.getFoodValue()+"");
		result.put("dragonTotalLevel",dragon.getTotalLevel()+"");
		result.put("dragonLevelValue",dragon.getLevelValue()+"");
		result.put("dragonId",dragon.getDragonId()+"");
		result.put("coin", coin+"");

		return result;
	}
	
	@GetMapping(value = "/inven/list")
	public List<Map<String, String>> getInvenWithRequestAndResponse(HttpServletRequest request) {

		String userId = request.getParameter("userId");
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<ProductVO> invenList = invenService.getInventory(userId);
		
		for (int i = 0; i < invenList.size(); i++) {
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("productId",invenList.get(i).getProductId()+"");
			map.put("productImage", invenList.get(i).getProductImage());
			map.put("count",invenList.get(i).getCnt()+"");
			if(invenList.get(i).getCategory().equals("item")) {
				result.add(map);
			}
		}
		return result;
	}
	
	@PostMapping(value = "/inven/use")
	public List<Map<String, String>> useProductWithRequestAndResponse(HttpServletRequest request) throws ScriptException{
		
		String userId = request.getParameter("userId");
		int dragonId = Integer.parseInt(request.getParameter("dragonId"));
		int productId = Integer.parseInt(request.getParameter("productId"));
		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int value = invenService.valueSettingByItem(productId, userId, dragonId);
		invenService.use(userId, productId);
		
		List<ProductVO> invenList = invenService.getInventory(userId);
		
		
		for (int i = 0; i < invenList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("productId",invenList.get(i).getProductId()+"");
			map.put("productImage", invenList.get(i).getProductImage());
			map.put("count",invenList.get(i).getCnt()+"");
			if(invenList.get(i).getCategory().equals("item")) {
				result.add(map);
			}
		}
		Map<String, String> dragonValue = new HashMap<String, String>();
		DragonVO userInfo = new DragonVO(userId, dragonId, false);
		DragonVO dragon = service.getDragonByDragonId(userInfo);
		int coin = service.getCoin(userId);
		setImg(dragon);
		dragonValue.put("dragonImage",dragon.getImg());
		dragonValue.put("hungryValue", dragon.getFoodValue()+"");
		dragonValue.put("dragonTotalLevel",dragon.getTotalLevel()+"");
		dragonValue.put("dragonLevelValue",dragon.getLevelValue()+"");
		dragonValue.put("dragonId",dragon.getDragonId()+"");
		result.add(dragonValue);
		return result;
	}
	

	public DragonVO setImg(DragonVO vo) {// 이미지 셋팅

		int level = vo.getTotalLevel();
		HashMap<String, String> dragonList = service.getImageByLevel(vo.getDragonId());
		if (dragonList != null) {
			if (level < 10) {
				vo.setImg(dragonList.get("level0"));
			} else if (level >= 10 && level < 20) {
				vo.setImg(dragonList.get("level1"));
			} else if (level >= 20 && level < 30) {
				vo.setImg(dragonList.get("level2"));
			} else if (level >= 30) {
				vo.setImg(dragonList.get("level3"));
			}
		}
		return vo;
	}
}
