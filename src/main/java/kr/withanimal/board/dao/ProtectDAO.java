package kr.withanimal.board.dao;

import java.util.HashMap;
import java.util.List;

import kr.withanimal.board.vo.ProtectVO;

public interface ProtectDAO {
	// 1. 개수 구하기
	int selectCount(HashMap<String, Object> map);
	
	// 2. 1개 구하기 
	ProtectVO selectByIdx(int content_idx);
	 
	// 3. 1페이지 얻기
	List<ProtectVO> selectList(HashMap<String, Object> map);
	
	void insert(ProtectVO vo);
}
