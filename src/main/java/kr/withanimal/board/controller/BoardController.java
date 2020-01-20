package kr.withanimal.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.withanimal.board.service.BoardService;
import kr.withanimal.board.vo.BoardVO;
import kr.withanimal.board.vo.Paging;
import kr.withanimal.member.vo.MemberVO;
 
@Controller
public class BoardController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	
	@RequestMapping(value="/board/boardList")
	public String selectList(Model model, @RequestParam(required=false) Integer p,@RequestParam(required=false) Integer s,@RequestParam(required=false) Integer b) {
		int currentPage = p==null ? 1 : p;
		int pageSize = s==null ? 10 : s;
		int blockSize = b==null ? 10 : b;
		Paging<BoardVO> paging = boardService.selectList(currentPage, pageSize, blockSize);
		model.addAttribute("paging", paging);
		return "boardList";	
	}
	
	@RequestMapping(value="/board/boardForm")
	public String formInput(HttpServletRequest request) {
		// 로그인 판단
		HttpSession session = request.getSession();
		logger.debug(session.getAttribute("vo"));
		if(session.getAttribute("vo")==null) { // 로그인이 안되어 있으면
			return "redirect:/login";
		}
		return "boardForm";
	}
	
	@RequestMapping(value = "/board/boardResult", method=RequestMethod.POST)
	public String boardResult(HttpServletRequest request, Model model) {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("p"));
		}catch (Exception e) {
			;
		}
		int pageSize = 10;
		try {
			pageSize = Integer.parseInt(request.getParameter("s"));
		}catch (Exception e) {
			;
		}
		int blockSize = 10;
		try {
			blockSize = Integer.parseInt(request.getParameter("b"));
		}catch (Exception e) {
			;
		}
		int idx = -1;
		try {
			idx = Integer.parseInt(request.getParameter("idx"));
		}catch (Exception e) {
			;
		}
		
		BoardVO vo = boardService.selectByIdx(idx);
		logger.info("vo~~~~~~~~~~~"+vo);
		if(vo==null) {
			return "redirect:/board/boardList";
		}
		logger.info("p :"+currentPage+",s :"+pageSize+",b :"+blockSize);
		logger.info("vo :"+vo); 
		model.addAttribute("vo",vo);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("blockSize",blockSize);
		return "boardResult";
	}
	
	@RequestMapping(value = "/board/boardResult",method=RequestMethod.GET)
	public String boardResultGet() {
		return "redirect:/board/boardForm";
	}
	
	
	
	@RequestMapping(value = "/board/insertOk", method=RequestMethod.POST)
	public String insertOkPost(HttpServletRequest request,@ModelAttribute BoardVO boardVO,Model model) {
		logger.info("boardResult boardVO : "+boardVO);
		MemberVO vo = (MemberVO) request.getSession().getAttribute("vo");
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("p"));
		}catch (Exception e) {
			;
		}
		int pageSize = 10;
		try {
			pageSize = Integer.parseInt(request.getParameter("s"));
		}catch (Exception e) {
			;
		}
		int blockSize = 10;
		try {
			blockSize = Integer.parseInt(request.getParameter("b"));
		}catch (Exception e) {
			;
		}
		int idx = -1;
		try {
			idx = Integer.parseInt(request.getParameter("idx"));
		}catch (Exception e) {
			;
		}
		logger.info("boardResult vo : "+vo); 		 
		boardVO.setU_id(vo.getU_id());
		boardVO.setU_pw(vo.getU_pw());
		boardVO.setU_name(vo.getU_name());
		boardVO.setIp(request.getLocalAddr());
		logger.info("boardResult boardVO : "+boardVO);
		System.out.println("======================="+boardVO);
		boardService.insert(boardVO);
		model.addAttribute("vo",vo);
		model.addAttribute("subject",boardVO.getSubject());
		model.addAttribute("content",boardVO.getContent());
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("blockSize",blockSize);
		if(vo==null) {
			return "redirect:/board/boardForm";
		}
		return "redirect:/board/boardList";
	}

	@RequestMapping(value = "/board/insertOk",method=RequestMethod.GET)
	public String insertOkGet() {
		return "redirect:/board/boardForm";
	}
	
	
	
	@RequestMapping(value="/board/updateForm")
	public String updateForm(HttpServletRequest request,@RequestParam(required=false) int idx ,Model model) {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("p"));
		}catch (Exception e) {
			;
		}
		int pageSize = 10;
		try {
			pageSize = Integer.parseInt(request.getParameter("s"));
		}catch (Exception e) {
			;
		}
		int blockSize = 10;
		try {
			blockSize = Integer.parseInt(request.getParameter("b"));
		}catch (Exception e) {
			;
		}
		if(idx==0) {
			return "redirect:/board/boardList";
		}
		BoardVO vo = boardService.selectByIdx(idx);
		
		HttpSession session = request.getSession();
		logger.debug("updateForm session vo: "+session.getAttribute("vo"));
		model.addAttribute("vo",vo);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("blockSize",blockSize);
		logger.info("currentPage :"+currentPage+",pageSize :"+pageSize+",blockSize :"+blockSize);
		logger.info("vo :"+vo); 
		System.out.println("currentPage :"+currentPage+",pageSize :"+pageSize+",blockSize :"+blockSize);
		if(session.getAttribute("vo")==null||vo==null) { 
			return "redirect:/login";
		}
		return "updateForm";
	}
	
	@RequestMapping(value="/board/updateOk",method=RequestMethod.POST)
	public String updateOkPost(HttpServletRequest request,@ModelAttribute BoardVO boardVO,Model model
							  ,@RequestParam(required=false) int idx) {
			logger.info("boardResult boardVO ************ "+boardVO);
			MemberVO vo = (MemberVO) request.getSession().getAttribute("vo");
			int currentPage = 1;
			try {
				currentPage = Integer.parseInt(request.getParameter("p"));
			}catch (Exception e) {
				;
			}
			int pageSize = 10;
			try {
				pageSize = Integer.parseInt(request.getParameter("s"));
			}catch (Exception e) {
				;
			}
			int blockSize = 10;
			try {
				blockSize = Integer.parseInt(request.getParameter("b"));
			}catch (Exception e) {
				;
			}
			
			logger.info("boardResult vo : "+vo); 	
			boardVO.setContent_idx(idx);
			boardVO.setU_id(vo.getU_id());
			boardVO.setU_pw(vo.getU_pw());
			boardVO.setU_name(vo.getU_name());
			boardVO.setIp(request.getLocalAddr());
			logger.info("boardResult boardVO : "+boardVO);
			System.out.println("======================="+boardVO);
			boardService.update(boardVO);
			model.addAttribute("vo",vo);
			model.addAttribute("subject",boardVO.getSubject());
			model.addAttribute("content",boardVO.getContent());
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("pageSize",pageSize);
			model.addAttribute("blockSize",blockSize);
			
			if(vo==null) {
				return "redirect:/board/boardList";
			}
		return "redirect:/board/boardList";
	}

	@RequestMapping(value = "/board/updateOk",method=RequestMethod.GET)
	public String updateOkGet() {
		return "redirect:/board/boardList";
	}

	
	
	@RequestMapping(value="/board/deleteOk",method=RequestMethod.POST)
	public String deleteOkPost(HttpServletRequest request,@ModelAttribute BoardVO boardVO,Model model
							  ,@RequestParam(required=false) int idx) {
			logger.info("boardResult boardVO : "+boardVO);
			MemberVO vo = (MemberVO) request.getSession().getAttribute("vo");
			int currentPage = 1;
			try {
				currentPage = Integer.parseInt(request.getParameter("p"));
			}catch (Exception e) {
				;
			}
			int pageSize = 10;
			try {
				pageSize = Integer.parseInt(request.getParameter("s"));
			}catch (Exception e) {
				;
			}
			int blockSize = 10;
			try {
				blockSize = Integer.parseInt(request.getParameter("b"));
			}catch (Exception e) {
				;
			}
			
			logger.info("boardResult vo : "+vo); 	
			boardVO.setContent_idx(idx);
			boardVO.setU_id(vo.getU_id());
			boardVO.setU_pw(vo.getU_pw());
			boardVO.setU_name(vo.getU_name());
			boardVO.setIp(request.getLocalAddr());
			logger.info("boardResult boardVO : "+boardVO);
			System.out.println("======================="+boardVO);
			boardService.delete(boardVO);
			model.addAttribute("vo",vo);
			model.addAttribute("subject",boardVO.getSubject());
			model.addAttribute("content",boardVO.getContent());
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("pageSize",pageSize);
			model.addAttribute("blockSize",blockSize);
			
			if(vo==null) {
				return "redirect:/board/boardList";
			}
		return "redirect:/board/boardList";
	}

	@RequestMapping(value = "/board/deleteOk",method=RequestMethod.GET)
	public String deleteOkGet() {
		return "redirect:/board/boardList";
	}
	
	
	// Post 방식 파일 업로드
	@RequestMapping(value = "/board/profileImage", produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String multiUploadFormPOST(MultipartHttpServletRequest request) throws Exception {
		logger.info("multiUploadFormPOST");
		// 서버의 실제 경로
		@SuppressWarnings("deprecation")
		String realPath = request.getRealPath(uploadPath);
		logger.info("서버경로 : " + realPath);
		// 파일받기
		List<MultipartFile> files = request.getFiles("file");
		logger.info("files : " + files);
		if (files == null || files.size() == 0) {
			return "redirect:/board/boardForm";
		}
		String savedName = "";
		for (MultipartFile file : files) {
			if (file != null && file.getSize() > 0) {
				savedName = uploadFile(request, realPath, file.getOriginalFilename(), file.getBytes());
			}
		}
		return savedName;
	}

	// 업로드된 파일을 저장하는 함수
	private String uploadFile(MultipartHttpServletRequest request, String realPath, String originalName, byte[] fileData) throws IOException {
		UUID uid = UUID.randomUUID();
		String savedName = uid.toString() + "_" + originalName;
		File target = new File(realPath, savedName);
		// org.springframework.util 패키지의 FileCopyUtils는 파일 데이터를 파일로 처리하거나, 복사하는 등의 기능이
		// 있다.
		FileCopyUtils.copy(fileData, target);
		String contextPath = request.getContextPath();
		logger.info("********contextPath : " + contextPath);
		return contextPath + "/upload/" + savedName; // 이미지가 저장된 URL을 리턴하면 된다.
	}
	
}
