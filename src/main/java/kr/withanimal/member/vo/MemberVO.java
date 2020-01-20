package kr.withanimal.member.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
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
*/
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberVO {
	private int idx;
	private String u_id;
	private String u_pw;
	private String u_name;
	private Date   u_birth;
	private String u_phone;
	private String u_zipcode;
	private String u_addr1;
	private String u_addr2;
	private int    u_use;
	private Date   u_regdate;
}
