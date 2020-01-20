package kr.withanimal.board.service;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.withanimal.board.dao.BoardDAO;
import kr.withanimal.board.vo.BoardVO;
import kr.withanimal.board.vo.Paging;

@Service
public class BoardService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BoardService.class);
	
	@Autowired
	private BoardDAO boardDAO;
	
	// 1개 가져오기
	public BoardVO selectByIdx(int idx) {
		return boardDAO.selectByIdx(idx);
	}
	
	
	// 1. 목록보기
	public Paging<BoardVO> selectList(int currentPage, int pageSize,int blockSize){
		int totalCount = boardDAO.selectCount();
		Paging<BoardVO> paging = new Paging<BoardVO>(totalCount, currentPage, pageSize, blockSize);
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startNo", paging.getStartNo());
		map.put("endNo", paging.getEndNo());
		List<BoardVO> list = boardDAO.selectList(map);
		paging.setList(list);
		return paging;
	}
	
	// 2. 저장하기
	public void insert(BoardVO boardVO) {
		logger.info("**BoardService insert : "+boardVO);
		boardDAO.insert(boardVO);		
	}
	
	// 3. 수정하기
	public void update(BoardVO boardVO) {
		logger.info("**BoardService update : "+boardVO);
		BoardVO dbVO = boardDAO.selectByIdx(boardVO.getContent_idx());
			boardDAO.update(boardVO);			
	}
	
	// 4. 삭제하기
	public void delete(BoardVO boardVO) {
		BoardVO dbVO = boardDAO.selectByIdx(boardVO.getContent_idx());
		if(boardVO!=null&& dbVO!=null&& boardVO.getU_pw().equals(dbVO.getU_pw())) {
			boardDAO.delete(boardVO.getContent_idx());
		}
	}
}
