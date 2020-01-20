package kr.withanimal.member.service;

import java.util.HashMap;
import java.util.Random;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import kr.withanimal.member.dao.MemberDAO;
import kr.withanimal.member.vo.MemberVO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	@Autowired
	private JavaMailSender mailSender;
	
	// 1. 저장  void insert(MemberVO vo);
	public void insert(MemberVO memberVO) {
		// 1) 데이터 검증
		if(memberVO==null || memberVO.getU_id()==null) return;
		
		// 2) DB 저장
		memberDAO.insert(memberVO);
		
		// 3) 회원가입 축하메일 전송
		sendEmail(memberVO);
	}
	
	private void sendEmail(MemberVO memberVO) { // 메일 전송
		MimeMessagePreparator preparator = getMessgePreparator(memberVO);
		try {
			mailSender.send(preparator);
			System.out.println("메일보내기 성공!!!!");
		}catch (MailException ex) {
			System.err.println(ex.getMessage());
		}	
	}
	private MimeMessagePreparator getMessgePreparator(final MemberVO memberVO) { // 축하메일 메서드
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				//mimeMessage.setFrom("sungnam201908@gmail.com");
				mimeMessage.setFrom(memberVO.getU_id());
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(memberVO.getU_id()));
				mimeMessage.setContent("<p><span style=\'font-size: 16pt;\'>반갑습니다."+memberVO.getU_name()+"님 </span></p>"
								+ "<p><span style=\'font-size: 16pt;\'>With Animal에 회원가입을 축하드립니다. </span></p>"
								+ "<p><br></p><p><span style=\'font-size: 13pt;\'>회원가입을 완료하려면 다음의 링크를 클릭해서 인증해주세요.</span></p>"
								+ "<a href='http://localhost:8080/member/confirm?u_id="+ memberVO.getU_id()+"'>인증</a><br>",
								"text/html;charset=UTF-8");
				mimeMessage.setSubject("회원 가입을 축하드립니다.");
			}
		};
		return preparator;
	}
	
	// 회원 이메일 인증  void updateUse(HashMap<String, String> map);
	public void confirm(String u_id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("u_id", u_id);
		MemberVO vo = memberDAO.selectByUserID(map);
		
		//DB 변경
		if(vo!=null) {
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("u_id", u_id);
			map2.put("u_use", "2"); //0은 인증전, 1은 휴면, 2 인증완료
			memberDAO.updateUse(map2);
		}		
	}
	
	// 아이디 중복 검사
	public String idCheck(String u_id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("u_id", u_id);
		MemberVO vo = memberDAO.selectByUserID(map);		
		return vo == null ? "0":"1";
	}
	
	// 로그인 처리
	public MemberVO loginOk(MemberVO memberVO) {
		MemberVO vo = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("u_id", memberVO.getU_id());
		vo = memberDAO.selectByUserID(map);
		if(vo!=null) {
			if(!vo.getU_pw().equals(memberVO.getU_pw())) {
				vo = null;
			}
		}
		return vo;
	}

	// 6. 개수 얻기 int getCount();
	public int getCount() {
		return memberDAO.getCount();
	}
	
	// 7. 1개 읽기(u_id로 얻기) : 로그인, 비번 찾기 --> 이메일로 비번 전송
	//     MemberVO selectByUserID(HashMap<String, String> map);
	public MemberVO passwordSearch(MemberVO memberVO) {
		MemberVO vo = null;
		HashMap<String , String> map = new HashMap<String, String>();
		map.put("u_id", memberVO.getU_id());
		vo = memberDAO.selectByUserID(map);
		if(!(vo!=null && vo.getU_name().equals(memberVO.getU_name()) && vo.getU_phone().equals(memberVO.getU_phone()))) {
			vo = null;
		}else {
			// 임시 비밀번호를 만들어서 사용자에게 메일을 발송
			// 1) 임시 비번 생성
			StringBuilder sb = new StringBuilder();
			Random rnd = new Random();
			for(int i=0; i<4; i++) {
				sb.append((char)(rnd.nextInt(26)+'A')+"");
				sb.append((char)(rnd.nextInt(26)+'a')+"");
				sb.append((char)(rnd.nextInt(10)+'0')+"");
			}
			final String u_pw = sb.toString();
			
			// 2) 임시 비번으로 DB를 업데이트 해주고
			HashMap<String, String> map2 = new HashMap<String, String>();
			map2.put("u_id", memberVO.getU_id());
			map2.put("u_pw", u_pw);
			memberDAO.updatePassword(map2);
			
			// 3) 메일 발송
			try {
				final MemberVO vo2 = vo;
				MimeMessagePreparator preparator = new MimeMessagePreparator() {
					
					@Override
					public void prepare(MimeMessage mimeMessage) throws Exception {
						//mimeMessage.setFrom("sungnam201908@gmail.com");
						mimeMessage.setFrom(vo2.getU_id());
						mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(vo2.getU_id()));
						//mimeMessage.setText("반갑습니다."+vo2.getU_name()+"님 <br>"
						mimeMessage.setContent("<p><span style=\'font-size: 16pt;\'>반갑습니다."+vo2.getU_name()+"님 </span></p>"
										+ "<p><span style=\'font-size: 16pt;\'>With Animal에서 임시 비밀번호를 보냅니다.</span></p>"
										+ "<p><span style=\'font-size: 13pt;\'>비밀번호는 꼭 다시 설정해주세요.</span></p>"
										+ "<p><br></p>"
										+ "<p><span style=\'font-size: 13pt;\'>[임시 비밀번호]: <b>"+u_pw+"</b></span></p>", "text/html; charset=UTF-8");
										
						mimeMessage.setSubject("임시 비밀번호입니다.");
					}
				};
				mailSender.send(preparator);
				System.out.println("메일 보내기 성공!!!!");
			}catch(MailException e){
				System.err.println(e.getMessage());
			}
		}
		return vo;
	}
	
	// 8. 1개 읽기(이름과 전화번호) : 아이디 찾기 --> 화면 표시
	// 	  MemberVO selectByUserName(HashMap<String, String> map);
	public MemberVO idSearch(MemberVO memberVO) {
		MemberVO vo = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("u_name", memberVO.getU_name());
		map.put("u_phone", memberVO.getU_phone());
		vo = memberDAO.selectByUserName(map);
		return vo;
	}
}
