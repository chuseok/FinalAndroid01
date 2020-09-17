package org.dragon.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.login.domain.AuthVO;
import org.login.domain.MemberVO;
import org.login.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.tst.NaverLoginBO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller("web.controller.MemberController")
@Log4j
@AllArgsConstructor
public class MemberController {

	private MemberService service;
	
	@GetMapping("/main/home1")
	public void list(Model model) {
		log.info("home1");
		
		model.addAttribute("home1", service.getList());
	}
	//?öå?õêÍ∞??ûÖ
	@PostMapping("/main/join")
	public String register(@ModelAttribute("mem") MemberVO mem,@ModelAttribute("vo") AuthVO vo, RedirectAttributes rttr) {
		log.info("join: " + mem);
		log.info("auth...."+vo);
		
		int result = service.idCheck(mem);
		int result2 = service.nameCheck(mem);
		
		try {
			if(result ==1 && result2 ==1) {
				return "/main/home1";
			}else if(result == 0) {
				service.register(mem);
				service.register(vo);			
			}
			// ?ûÖ?†•?êú ?ïÑ?ù¥?îîÍ∞? Ï°¥Ïû¨?ïú?ã§Î©? -> ?ã§?ãú ?öå?õêÍ∞??ûÖ ?éò?ù¥Ïß?Î°? ?èå?ïÑÍ∞?Í∏? 
			// Ï°¥Ïû¨?ïòÏß? ?ïä?äî?ã§Î©? -> register
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		
		rttr.addFlashAttribute("result",mem.getUserId());
		rttr.addFlashAttribute("result",vo.getUserId());
		
		
		return "redirect:/main/home1";
	}
	@GetMapping("/main/join")
	public void join() {
		
	}
	// ?öå?õê ?Éà?á¥ get
	@RequestMapping(value="/withdrawal", method = RequestMethod.GET)
	public String withdrawalView(){
		return "/withdrawal";
	}
	
	// ?öå?õê ?Éà?á¥ post
	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
public String withdrawal(@ModelAttribute("mem") MemberVO mem,@ModelAttribute("vo") AuthVO auth,HttpSession session,
		Model model, RedirectAttributes rttr){
		log.info("withdrawal: " + mem);
		log.info("removeauth...."+auth);
		
		int result = service.PwdCheck(mem);
		try {
			if(result ==1) {
				
				service.withdrawal(auth);
				service.withdrawal(mem);
				
			}else if(result == 0) {
				return "redirect:/main/home1";
			}
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		
		rttr.addFlashAttribute("result",mem.getUserId());
		rttr.addFlashAttribute("result",auth.getUserId());
		
		
		return "redirect:/main/home1";
	}
		
	
	@GetMapping("/main/update")
	public void updateView() {
		
	}
	
	@RequestMapping(value = "/main/update", method = RequestMethod.POST)
	public String updateView(@ModelAttribute("mem") MemberVO mem, RedirectAttributes rttr){
			log.info("updateView: " + mem);
			
			
			int result = service.PwdCheck(mem);
			try {
				if(result ==1) {
					return "redirect:/main/updateFrm";
					
				}else if(result == 0) {
					return "redirect:/main/update";
				}
				
			} catch (Exception e) {
				throw new RuntimeException();
			}
			rttr.addFlashAttribute("result",mem.getUserId());

			return "/";
		}
	@GetMapping("/main/updateFrm")
	public void update() {
		
	}
	//?ãâ?Ñ§?ûÑ Î≥?Í≤?
	@PostMapping("/main/updateFrm")
	public String update(@ModelAttribute("mem") MemberVO mem, RedirectAttributes rttr) {
		log.info("update userName: " + mem);
		
		int result = service.nameCheck(mem);
		
		try {
			if(result ==1) {
				return "/main/updateFrm";
			}else if(result == 0) {
				service.modify(mem);
			}
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		
		rttr.addFlashAttribute("result",mem.getUserId());
		
		
		return "redirect:/main/home1";
	}
	@GetMapping("/main/updateFrm2")
	public void update2() {
		
	}
	//ÎπÑÎ?Î≤àÌò∏ Î≥?Í≤?
	@PostMapping("/main/updateFrm2")
	public String update2(@ModelAttribute("mem") MemberVO mem, RedirectAttributes rttr) {
		log.info("update password: " + mem);
		
				service.modify2(mem);
				
		rttr.addFlashAttribute("result",mem.getUserId());
		
		
		return "redirect:/main/home1";
	}
	//?ï∏?ìú?è∞ Î≤àÌò∏ Î≥?Í≤?
	@GetMapping("/main/updateFrm3")
	public void update3() {
		
	}
	//?ï∏?ìú?è∞ Î≤àÌò∏ Î≥?Í≤?
	@PostMapping("/main/updateFrm3")
	public String update3(@ModelAttribute("mem") MemberVO mem, RedirectAttributes rttr) {
		log.info("update phoneNo: " + mem);
		
				service.modify3(mem);
				
		rttr.addFlashAttribute("result",mem.getUserId());
		
		
		return "redirect:/main/home1";
	}
	//?ù¥Î©îÏùºÏ£ºÏÜå Î≥?Í≤?
		@GetMapping("/main/updateFrm4")
		public void update4() {
			
		}
		//?ï∏?ìú?è∞ Î≤àÌò∏ Î≥?Í≤?
		@PostMapping("/main/updateFrm4")
		public String update4(@ModelAttribute("mem") MemberVO mem, RedirectAttributes rttr) {
			log.info("update email: " + mem);
			
					service.modify4(mem);
					
			rttr.addFlashAttribute("result",mem.getUserId());
			
			
			return "redirect:/main/home1";
		}
    //?öå?õêÍ∞??ûÖ Ï≤¥ÌÅ¨
	@ResponseBody
	@RequestMapping(value="/main/idCheck", method = RequestMethod.POST)
	public int idChk(MemberVO mem) throws Exception {
		int result = service.idCheck(mem);
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/main/nameCheck", method = RequestMethod.POST)
	public int nameCheck(MemberVO mem) throws Exception {
		log.info("hello");
		int result = service.nameCheck(mem);
		return result;
	}
	
	//?öå?õê?Éà?á¥,?†ïÎ≥¥Î?Í≤ΩÏãú ?ïÑ?öî?ïú ÎπÑÎ?Î≤àÌò∏Ï≤¥ÌÅ¨
	@ResponseBody
	@RequestMapping(value="/main/PwdCheck", method = RequestMethod.POST)
	public int PwdChk(MemberVO mem) throws Exception {
		int result = service.PwdCheck(mem);
		return result;
	}

    @RequestMapping(value="./customLogin", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(Model model, HttpSession session) {
        String naverAuthUrl = NaverLoginBO.getAuthorizationUrl(session);
        
      
        System.out.println("?ç†?èô?òô?ç†?ã±Î±ÑÏòô:" + naverAuthUrl);
        
        model.addAttribute("url", naverAuthUrl);

        return "/customLogin";
    }
	
	
}
