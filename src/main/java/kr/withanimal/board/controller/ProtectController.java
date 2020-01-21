package kr.withanimal.board.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.withanimal.board.service.ProtectService;
import kr.withanimal.board.vo.BoardVO;
import kr.withanimal.board.vo.Paging;
import kr.withanimal.board.vo.ProtectVO;
import kr.withanimal.member.vo.MemberVO;

@Controller
public class ProtectController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProtectController.class);	
	
	@Autowired
	private ProtectService protectService;
	/*
	@RequestMapping(value="/board/protectList")
	public String protectList(Model model, @RequestParam(required=false) Integer p,@RequestParam(required=false) Integer s,@RequestParam(required=false) Integer b) throws MalformedURLException, IOException {
		int currentPage = p==null ? 1 : p;
		int pageSize = s==null ? 10 : s;
		int blockSize = b==null ? 10 : b;
		
		Date time = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyyMM");
		SimpleDateFormat format2 = new SimpleDateFormat ("yyyyMMdd");
		String bgnde = format1.format(time)+"01";
		String endde = format2.format(time);		
		List<ProtectVO> list = protectService.readJson(bgnde,endde);
		Paging<ProtectVO> paging = protectService.selectList(list, currentPage, pageSize, blockSize);		
		model.addAttribute(paging);		
		return "redirect:/board/readJSON";	
	}*/
	@RequestMapping(value="/board/protectList")
	public String protectList(Model model, @RequestParam(required=false) Integer p
			,@RequestParam(required=false) String bgnde, @RequestParam(required=false) String endde
			,@RequestParam(required=false) String kindCd, @RequestParam(required=false) String processState) 
			throws MalformedURLException, IOException{
		int currentPage = p==null ? 1 : p;
		System.out.println("ProtectController bgnde:  "+bgnde+", endde: "+endde);
		System.out.println("ProtectController kindCd:  "+kindCd+" processState : "+processState);
		System.out.println("-----------------------------------------------------------------------------");
		
		Date day = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat ("yyyyMM");
		SimpleDateFormat format2 = new SimpleDateFormat ("yyyyMMdd");
		
		if(bgnde==null||bgnde.trim().equals("")) bgnde =format1.format(day)+"01";
		if(endde==null||endde.trim().equals("")) endde =format2.format(day);

		Paging<ProtectVO> paging = protectService.selectList(bgnde, endde, kindCd, processState, currentPage);
		model.addAttribute("paging",paging);		
		model.addAttribute("bgnde",bgnde);		
		model.addAttribute("endde",endde);		
		model.addAttribute("kindCd",kindCd);	
		model.addAttribute("processState",processState);			
		return "protectList";
	}
	

	@RequestMapping(value="/board/protectResult", method=RequestMethod.POST) 
	public String protectResultPost(HttpServletRequest request, Model model, @RequestParam(required=false) int idx
			,@RequestParam(required=false) String bgnde, @RequestParam(required=false) String endde) throws MalformedURLException, IOException {
		if(idx<=1) return "redirect:/board/protectList";	
		bgnde = request.getParameter(bgnde);
		endde =request.getParameter(endde);
		
		System.out.println("protectResultPost bgnde:  "+bgnde);
		System.out.println("protectResultPost endde:  "+endde);

	    ProtectVO vo = protectService.selectByIdx(idx);
	    System.out.println("protectResultPost vo: "+vo);
		if(vo==null) {
			return "redirect:/board/protectList";
		}
		model.addAttribute("vo",vo); 
		return "protectResult";
	} 
	
	@RequestMapping(value="/board/protectResult", method=RequestMethod.GET) 
	public String protectResultGet()  {
		return "redirect:/board/protectList";
	} 
	
	@RequestMapping(value="/board/protectData")
	public String insertData() { 
		return "protectData";	
	}
	
	// XML 데이터 저장
	@RequestMapping(value="/board/saveXml")
	public String saveXml(Model model, @RequestParam(required=false) String bgnde, @RequestParam(required=false) String endde) 
			throws MalformedURLException, IOException{
		System.out.println("ProtectController insertData bgnde : "+bgnde+"endde : "+endde);
		if(bgnde==null||endde==null) return "redirect:/board/protectData";
		System.out.println("ProtectController insertData bgnde:  "+bgnde);
		System.out.println("ProtectController insertData endde:  "+endde);
		System.out.println("----------------------------------------------------------------------------");
		
		protectService.saveXml(bgnde, endde);
		
		System.out.println("saveXml xml파일 생성 완료");
		return "redirect:/board/protectData";
	}
	

	
	@RequestMapping(value = "/board/insertList")
	public String insertList(HttpServletRequest request,@ModelAttribute ProtectVO protectVO,Model model) {

		Date day = new Date();
		SimpleDateFormat format = new SimpleDateFormat ("yyyyMMdd");
		
		String bgnde =format.format(day);		
		protectService.insertList(bgnde);
		
		System.out.println("ProtectController insertList "+bgnde+" 데이터 추가 완료");
		return "redirect:/board/protectData";
	}

}
