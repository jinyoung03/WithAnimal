package kr.withanimal.member.dao;

import java.util.HashMap;
import java.util.List;

import kr.withanimal.member.vo.MemberVO;

public interface MemberDAO {
	// 1. 저장
	void insert(MemberVO vo);
	
	// 2. 1개 읽기(idx로 얻기)
	MemberVO selectByIdx(int idx);
	
	// 3. 수정
	void update(MemberVO vo);
	
	// 4. 삭제
	void delete(int idx);
	
	// 5. 모두 읽기
	List<MemberVO> selectList();
	
	// 6. 개수 얻기
	int getCount();
	
	// 7. 1개 읽기(u_id로 얻기) : 로그인, 비번 찾기 --> 이메일로 비번 전송
	MemberVO selectByUserID(HashMap<String, String> map);
	
	// 8. 1개 읽기(이름과 전화번호) : 아이디 찾기 --> 화면 표시
	MemberVO selectByUserName(HashMap<String, String> map);
	
	// 9. use값 변경하기(0은 가입전, 1은 휴면, 2 가입하고 인증....)
	void updateUse(HashMap<String, String> map);
		
	// 10. password값 변경하기
	void updatePassword(HashMap<String, String> map);
}
