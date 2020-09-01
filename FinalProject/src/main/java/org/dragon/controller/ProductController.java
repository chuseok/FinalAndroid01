package org.dragon.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dragon.domain.InventoryVO;
import org.dragon.domain.ProductVO;
import org.dragon.service.InventoryService;
import org.dragon.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/product")
@RestController
@Log4j
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ProductController {

	private ProductService service;
	private InventoryService invenService;
	
	
	@GetMapping(value="/get", produces = {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ProductVO>> getList(){
		log.info("getList................");
		List<ProductVO> list = service.getList();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@GetMapping(value="/get/{name}", produces = {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<ProductVO>getListByName(@PathVariable("name")String name, Model model){
		log.info("get name : "+name );
		model.addAttribute("selectedProduct", service.getProductByName(name));
		return new ResponseEntity<>(service.getProductByName(name),HttpStatus.OK);
	}
	@GetMapping(value = "/check/{name}")
	public ResponseEntity<Boolean> checkProduct(@PathVariable("name")String name, Principal principal){
		log.info("check name : "+name);
		if(principal==null) {
			return null;
		}
		String userId = principal.getName();
		ProductVO target = service.getProductByName(name);
		Boolean check = invenService.check(userId, target.getProductId());
		
		return new ResponseEntity<>(check,HttpStatus.OK);
	}
	@GetMapping(value="/get/category/{category}", produces = {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<List<ProductVO>> getListByCategory(@PathVariable("category")String category){
		log.info("get category : "+category);
		return new ResponseEntity<>(service.getProductByCategory(category),HttpStatus.OK);
	}
	@PostMapping(value = "/use/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<ProductVO> useProduct(@PathVariable("id")int id, Principal principal, HttpServletRequest request){
		System.out.println("use : "+id);
		if(principal==null) {
			return null;
		}
		String userId = principal.getName();
		ProductVO use = invenService.use(userId, id);
		
		return new ResponseEntity<ProductVO>(use,HttpStatus.OK);
	}
	@PostMapping(value = "/total/{price}/{amount}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Integer> total(@PathVariable("price") String price,@PathVariable("amount") String amount){
		
		int total = Integer.parseInt(price)* Integer.parseInt(amount);
		return new ResponseEntity<>(total,HttpStatus.OK);
	}
	

}