package kr.withanimal.board.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import kr.withanimal.board.vo.BoardVO;

public interface BoardDAO {
	
	// 1. 개수 구하기
	int selectCount();
	
	// 2. 1개 구하기
	BoardVO selectByIdx(int content_idx);
	
	// 3. 1페이지 얻기
	List<BoardVO> selectList(HashMap<String, Object> map);
	
	// 4. 저장하기
	void insert(BoardVO vo);
	
	// 5. 수정하기
	void update(BoardVO vo);
		
	// 6. 삭제하기
	void delete(int content_idx);

}
