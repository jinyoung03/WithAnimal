package kr.withanimal.board.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
CREATE TABLE withanimal_lost(				
	content_idx		NUMBER			PRIMARY KEY NOT NULL,	
	u_id			VARCHAR2(50)	NOT NULL,	
	u_pw			VARCHAR2(50)	NOT NULL,	
	u_name			VARCHAR2(50)	NOT NULL,	
	content			VARCHAR2(500)	NULL,	
	content_regdate	TIMESTAMP		NULL		
);				
 */
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardVO {
	private int content_idx;
	private String u_id;
	private String u_pw;
	private String u_name;
	private String subject;
	private String content;
	private Date content_regdate;
	private String ip;

}
