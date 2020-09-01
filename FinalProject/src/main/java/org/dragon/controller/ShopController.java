package org.dragon.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.dragon.domain.DragonVO;
import org.dragon.domain.InventoryVO;
import org.dragon.domain.ProductVO;
import org.dragon.service.DragonService;
import org.dragon.service.InventoryService;
import org.dragon.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/shop/*")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ShopController {

	private ProductService service;
	private InventoryService invenService;
	private DragonService dragonService;

	@GetMapping("/shop")
	public void shop1(Principal principal, Model model) {
		if (principal == null) {
			model.addAttribute("alert", true);
			return;
		}
		
		String userId = principal.getName();
		
		model.addAttribute("itemList", service.getProductByCategory("item"));
		model.addAttribute("backgroundList", service.getProductByCategory("background"));
		model.addAttribute("eggList", service.getProductByCategory("egg"));
		model.addAttribute("coin", dragonService.getCoin(userId));
		
		
	}

	@PostMapping("/buy")
	public String buy(@RequestParam("productId") int productId, @RequestParam("buyAmount") int buyAmount,
			Principal principal, Model rttr) {
		String userId = principal.getName();

		InventoryVO inventory = new InventoryVO(idGenerater(), userId, productId, buyAmount, null);
		DragonVO dragon = new DragonVO(userId, 1, 0, 100, false);

		invenService.buy(inventory, dragon);
		return "redirect:/shop/shop";
	}

	private String idGenerater() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		String subNum = "";

		for (int i = 1; i <= 6; i++) {
			subNum += (int) (Math.random() * 10);
		}

		String orderId = ymd + subNum;
		return orderId;
	}
}
