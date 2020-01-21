-- 회원 정보 테이블
DROP TABLE withanimal_member;			
CREATE TABLE withanimal_member(			
	idx			NUMBER			PRIMARY KEY NOT NULL,
	u_id		VARCHAR2(50)	NOT NULL,
	u_pw		VARCHAR2(50)	NOT NULL,
	u_name		VARCHAR2(50)	NOT NULL,
	u_birth		TIMESTAMP		NULL,
	u_phone		VARCHAR2(20)	NOT NULL,
	u_zipcode	VARCHAR2(10)	NULL,
	u_addr1		VARCHAR2(100)	NULL,
	u_addr2		VARCHAR2(100)	NULL,
	u_use		NUMBER			NULL,
	u_regdate	TIMESTAMP		NOT NULL	
);			
SELECT * FROM withanimal_member;			
CREATE SEQUENCE withanimal_member_idx_seq;			

INSERT INTO withanimal_member VALUES (withanimal_member_idx_seq.nextval,'hgd@naver.com','123456','홍길동','1995-12-01','010-1111-1111','','','','0',SYSDATE);
COMMIT;
SELECT * FROM withanimal_member;


-- 반려동물 분실등록/제보
-- 갤러리 게시판
DROP TABLE withanimal_lost;					
CREATE TABLE withanimal_lost(					
	content_idx		NUMBER			PRIMARY KEY NOT NULL,	
	u_id			VARCHAR2(50)	NOT NULL,	
	u_pw			VARCHAR2(50)	NOT NULL,	
	u_name			VARCHAR2(50)	NOT NULL,	
	subject			VARCHAR2(400)	NULL,	
	content			VARCHAR2(3000)	NULL,	
	content_regdate	TIMESTAMP		NULL,	
	ip				VARCHAR2(40)	NULL	
);					
SELECT * FROM withanimal_lost;	
DROP SEQUENCE withanimal_lost_idx_seq;
CREATE SEQUENCE withanimal_lost_idx_seq;	

INSERT INTO withanimal_lost VALUES (withanimal_lost_idx_seq.nextval,'hgd@naver.com','123456','홍길동','게시물 등록합니다.','일빠다~~~',SYSDATE,'');
COMMIT;
SELECT * FROM withanimal_lost;	


insert into withanimal_lost values 
(withanimal_lost_idx_seq.nextval,'#{u_id}','#{u_pw}','#{u_name}','#{subject}','#{content}',SYSDATE,'#{ip}') ;


-- 유기동물 조회 서비스
-- 갤러리 게시판
DROP TABLE withanimal_protect;				
CREATE TABLE withanimal_protect	-- 크기 4배로 수정			
(	
	content_idx		NUMBER			PRIMARY KEY NOT NULL,	
	age				VARCHAR2(120)	NULL,	
	careAddr		VARCHAR2(600)	NULL,	
	careNm			VARCHAR2(200)	NULL,	
	careTel			VARCHAR2(56)	NULL,	
	chargeNm		VARCHAR2(80)	NULL,	
	colorCd			VARCHAR2(120)	NULL,	
	desertionNo		VARCHAR2(80)	NULL,	
	filename		VARCHAR2(400)	NULL,	
	happenDt		VARCHAR2(32)	NULL,	
	happenPlace		VARCHAR2(400)	NULL,	
	kindCd			VARCHAR2(200)	NULL,	
	neuterYn		VARCHAR2(4)		NULL,	
	noticeEdt		VARCHAR2(32)	NULL,	
	noticeNo		VARCHAR2(120)	NULL,	
	noticeSdt		VARCHAR2(32)	NULL,	
	officetel		VARCHAR2(56)	NULL,	
	orgNm			VARCHAR2(200)	NULL,	
	popfile			VARCHAR2(400)	NULL,	
	processState	VARCHAR2(40)	NULL,	
	sexCd			VARCHAR2(4)		NULL,	
	specialMark		VARCHAR2(600)	NULL,	
	weight			VARCHAR2(120)	NULL
	--,
	--bgnde			VARCHAR2(20)	NULL,	
	--endde			VARCHAR2(20)	NULL	
);	

-- 데이터 크기 전부 2배로 변경

DROP SEQUENCE withanimal_protect_idx_seq;
CREATE SEQUENCE withanimal_protect_idx_seq;
				
DELETE FROM WITHANIMAL_PROTECT;

insert into withanimal_protect  
		values (withanimal_protect_idx_seq.nextval, '2018(년생)', '경기도 양주시 남면 상수리 410-1', '한국동물구조관리협회', '031-867-9119',  
				'이재웅', '검줄/회', '411309201900610', 'http://www.animal.go.kr/files/shelter/2019/12/202001021101185_s.jpg', '20191231',
				'덕릉로62길 89 창3동주민센터 인근', '[고양이] 한국 고양이', 'N', '20200113', '서울-도봉-2020-00001',
				'20200106', '02-2091-4472', '서울특별시 도봉구', 'http://www.animal.go.kr/files/shelter/2019/12/202001021101185.jpg', '종료(자연사)',
				'M', '얼굴부종.좌안안구돌출및동공축동.우안안구출혈.비/구강출혈.하악골절.꼬리골절.하악질/경계/발버둥침.', '5(Kg)'
		);

SELECT * FROM withanimal_protect WHERE HAPPENDT >= '20191228' AND KINDCD LIKE '%' AND PROCESSSTATE LIKE '종료%' ORDER BY CONTENT_IDX;
SELECT * FROM withanimal_protect WHERE HAPPENDT >= '20191228' AND KINDCD LIKE '[고양이]%' ORDER BY CONTENT_IDX;
SELECT * FROM withanimal_protect WHERE HAPPENDT >= '20191228' AND KINDCD LIKE '[기타축종]%'  ORDER BY CONTENT_IDX;
SELECT * FROM withanimal_protect WHERE HAPPENDT = '20200121';
AND PROCESSSTATE LIKE '보호중%';

