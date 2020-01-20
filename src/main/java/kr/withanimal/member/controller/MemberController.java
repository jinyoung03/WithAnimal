package kr.withanimal.member.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.withanimal.member.service.MemberService;
import kr.withanimal.member.vo.MemberVO;

@Controller
public class MemberController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login")
	public String login(HttpServletRequest request, Model model) {
		System.out.println(model + "*************** login ***************************");
		Cookie[] cookies = request.getCookies();
		String referer = request.getHeader("referer");
		try {
			referer = referer.replace("http://localhost:8080/member/", "");
		}catch(Exception e) {};
		
		if(cookies!=null && cookies.length>0) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("u_id")) {
					model.addAttribute("u_id",cookie.getValue());
					break;
				}
			}
		}
		request.getSession().setAttribute("referer", referer);
		return "login";
	}
	
	@RequestMapping(value="/loginOk",method=RequestMethod.GET)
	public String loginOkGet() {
		return "redirect:/login";
	}

	
	@RequestMapping(value="/loginOk", method=RequestMethod.POST)
	public String loginOkPost(@ModelAttribute MemberVO memberVO, @RequestParam(required=false) String remember, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println(memberVO + "*************** loginOk ***************************");
		MemberVO vo = memberService.loginOk(memberVO);
		logger.info("loginOkPost referer : "+request.getSession().getAttribute("referer"));
		String referer = ""+request.getSession().getAttribute("referer");
		
		if(vo==null) {
			return "redirect:/login";
		}else {
			request.getSession().setAttribute("vo", vo); //세션 저장
			if(remember!=null && remember.equals("save")) {
				Cookie cookie = new Cookie("u_id", vo.getU_id());
				cookie.setMaxAge(60*60*24*7);
				response.addCookie(cookie);
			}else {
				Cookie cookie = new Cookie("u_id", "");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}		
			logger.info("loginOkPost referer : ["+referer + "]");
			if(referer.trim().equals("null")||referer.trim().equals("login")||referer.trim().equals("logout")||referer.trim().equals("join")||referer.trim().equals("idSearch")||referer.trim().equals("pwSearch")) {
				return "redirect:/";
			}
			return "redirect:/"+referer;
		}
	}
		
	@RequestMapping(value="/logout")
	public String logOut(HttpServletRequest request) {
		request.getSession().removeAttribute("vo");
		return "home";
	}
	
	@RequestMapping(value="/join")
	public String join() {
		return "join";
	}
	
	@RequestMapping(value="/joinOk",method=RequestMethod.GET)
	public String joinOkGet() {
		return "redirect:/login";
	}
	
	@RequestMapping(value="/joinOk",method=RequestMethod.POST)
	public String joinOkPost(@ModelAttribute MemberVO memberVO) {
		memberService.insert(memberVO);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/confirm")
	public String confirm(@RequestParam String u_id) {
		memberService.confirm(u_id);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/idCheck")
	@ResponseBody
	public String idCheck(@RequestParam String u_id) {
		String result = memberService.idCheck(u_id);
		return result;
	}
	
	@RequestMapping(value="/idSearch")
	public String idSearch() {
		return "idSearch";
	}
	
	@RequestMapping(value="/idSearchOk", method=RequestMethod.GET)
	public String idSearchOkGet() {
		return "redirect:/login";		
	}
	     
	@RequestMapping(value="/idSearchOk",method=RequestMethod.POST)
	public String idSearchOkPost(@ModelAttribute MemberVO memberVO, Model model){
		System.out.println(memberVO + "*************** idSearchOkPOST ***************************");
		MemberVO vo = memberService.idSearch(memberVO);
		if(vo==null) {
			return "redirect:/idSearch";
		}else {
			model.addAttribute("vo", vo);
			return "viewUserID";
		}		
	}
	
	@RequestMapping(value="/pwSearch")
	public String passwordSearch() {
		return "pwSearch";
	}
	
	@RequestMapping(value="/pwSearchOk",method=RequestMethod.GET)
	public String pwSearchOkGet() {
		return "redirect:/login";		
	}
	
	@RequestMapping(value="/pwSearchOk",method=RequestMethod.POST)
	public String pwSearchOkPost(@ModelAttribute MemberVO memberVO, Model model) {
		MemberVO vo = memberService.passwordSearch(memberVO);
		if(vo==null) {
			return "redirect:/pwSearch";
		}else {
			model.addAttribute("vo",vo);
			return "viewPassword";
		}
	}
}