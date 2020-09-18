package org.dragon.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.dragon.domain.DragonVO;
import org.dragon.domain.UserVO;
import org.dragon.service.DragonService;
import org.dragon.service.MainService;
import org.login.domain.MemberVO;
import org.login.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

/**
 * Handles requests for the application home page.
 */
@Controller
@AllArgsConstructor
@Component
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private MainService service;
	private DragonService dragonService;
	private MemberService memberService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@GetMapping("/main")
	public String main(Principal principal, Model model, HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession();
		if(principal!=null){
			String userId = principal.getName();
			DateFormat format = new SimpleDateFormat("ddHHmm");
			Date last = service.getDate(userId);
			//service.updateDate(userId);
			Date now = new Date();
			long diff = now.getTime()-last.getTime();
			
			if(diff<1000*60*60*24) {//占쏙옙占쌈시곤옙 占쏙옙占�
				diff = 0;
			}else {
				diff /= 1000*60*60*24;
			}
			
			
			List<DragonVO> list = dragonService.getAllDragonByUser(userId);
			for(DragonVO dragon : list) {
				int val = (int) (dragon.getFoodValue()-diff);
				if(val<0) {
					dragon.setFoodValue(0);
				}else {
					dragon.setFoodValue(val);
				}
				if(val<30) {
					session.setAttribute("hungry", "드래곤의 포만감이 30%미만입니다. 밥을 주세요!");
				}
				
				dragonService.updateDragon(dragon);
			}
			model.addAttribute("text", session.getAttribute("text"));
			model.addAttribute("hungry", session.getAttribute("hungry"));
		}
		

		return "main";
	}
	@RequestMapping("/vision")
	@ResponseBody
	public List<Map<String, String>> androidTestWithRequestAndResponse(HttpServletRequest request){

        List<MemberVO> MemList = new ArrayList<MemberVO>();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        
        MemList = memberService.getList();
        
        
       
        for(int i=0;i<MemList.size();i++) {
           Map<String, String> map = new HashMap<String, String>();
           String userId = MemList.get(i).getUserId();
           String userPwd = MemList.get(i).getUserPwd();
           String email = MemList.get(i).getEmail();
           String userName = MemList.get(i).getUserName();
           map.put("userId",userId);
           map.put("userPwd", userPwd);
           map.put("email", email);
           map.put("userName", userName);
           
           result.add(map);
        }

        return result;
        }
   
	
	
}
