package kr.withanimal.board.vo;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProtectVO {
	private int    content_idx;
	private String age;
	private String careAddr;
	private String careNm;
	private String careTel;
	private String chargeNm;
	private String colorCd;
	private String desertionNo;
	private String filename;
	private String happenDt;
	private String happenPlace;
	private String kindCd;
	private String neuterYn;
	private String noticeEdt;
	private String noticeNo;
	private String noticeSdt;
	private String officetel;
	private String orgNm;
	private String popfile;
	private String processState;
	private String sexCd;
	private String specialMark;
	private String weight;
}
