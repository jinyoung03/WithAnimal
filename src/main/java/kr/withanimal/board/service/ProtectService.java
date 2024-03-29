package kr.withanimal.board.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.withanimal.board.dao.ProtectDAO;
import kr.withanimal.board.vo.BoardVO;
import kr.withanimal.board.vo.Paging;
import kr.withanimal.board.vo.ProtectVO;

@Service
public class ProtectService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ProtectService.class);
	
	@Autowired
	private ProtectDAO protectDAO;
	
	public int INDENT_FACTOR = 4;    
    
	// 1. 목록보기
	public Paging<ProtectVO> selectList(String bgnde,String endde, String kind,String state
			,int currentPage) throws MalformedURLException, IOException{
		String kindCd,processState;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bgnde", bgnde);
		map.put("endde", endde);
				
		if(kind==null) kindCd="";
		else if(kind.equals("dog")) kindCd="[개]";
		else if(kind.equals("cat")) kindCd="[고양이]";
		else if(kind.equals("etc")) kindCd="[기타축종]";
		else kindCd="";
		
		if(state == null) processState ="";
		else if(state.equals("end")) processState ="종료";
		else if(state.equals("ongoing")) processState ="보호중";
		else processState = "";
		
		map.put("processState", processState);
		map.put("kindCd", kindCd);  
		
		int totalCount = protectDAO.selectCount(map);

		Paging<ProtectVO> paging = new Paging<ProtectVO>(totalCount, currentPage, 24, 10);		
		map.put("startNo", paging.getStartNo());
		map.put("endNo", paging.getEndNo());
		List<ProtectVO> list = protectDAO.selectList(map);
		paging.setList(list);
		return paging;
	}	 	
	 
	public List<ProtectVO> getPage(List<ProtectVO> list, int startNo, int endNo){
		List<ProtectVO> currentPage = new ArrayList<ProtectVO>();
//		System.out.println("ProtectService getPage "+list);
//		System.out.println("ProtectService getPage startNo "+startNo);
//		System.out.println("ProtectService getPage endNo "+endNo);
		for(ProtectVO vo : list) {
//			System.out.println("ProtectService getPage Content_idx"+vo.getContent_idx());
			if(vo.getContent_idx()>=startNo&&vo.getContent_idx()<endNo)
			currentPage.add(vo);
		}
		return currentPage;
	}
	
	// 1개 가져오기
	public ProtectVO selectByIdx(int idx) throws MalformedURLException, IOException{	
		return protectDAO.selectByIdx(idx);
	}
	

	// 링크에서 전체 개수 가져오는 메서드
	public int getJsonTotalCount(String bgnde, String endde) throws MalformedURLException,IOException {
		int totalCount=0;
		try {
	    	String strUrl =	"http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
	    					+ "bgnde=" +bgnde
	    					+ "&endde=" +endde
	    					+ "&numOfRows=0" // totalCount만 받아올거라 전체개수를 0으로 함
	    					+ "&ServiceKey=O2GKwCxkLW84OZXlbc7wuiJE8cAieTSwRG%2FVOe3KQVKnFOoOpxqzYt7CsWezKxah0HuRCGpKAMj%2FqA7lhOG0Vg%3D%3D";
	        HttpURLConnection conn = (HttpURLConnection) new URL(strUrl).openConnection();
	        conn.connect();
	        
	        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
	        StringBuffer st = new StringBuffer();
	        String line;
	        
	        while ((line = reader.readLine()) != null) {    
	        	st.append(line);
	        }
	     
	        JSONObject xmlJSONObj = XML.toJSONObject(st.toString());
	        String jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
	        
	        //System.out.println(xmlJSONObj.get("response"));
	        JSONObject job = (JSONObject) xmlJSONObj.get("response");
	        //System.out.println(job.get("body").toString());
	        
	        JSONObject j2 = (JSONObject) job.get("body");
	        String j3 = j2.get("totalCount").toString();
	        totalCount = Integer.parseInt(j3);
			System.out.println("service getJsonTotalCount : "+j3);
	        
	        } catch (JSONException je) {
	            System.out.println(je.toString());
	        }
		return totalCount;
	}
	
	//xml 파일로 저장하기
	public void saveXml(String bgnde,String endde){		
		try {
			int totalCount = getJsonTotalCount(bgnde, endde);
			int max = 500; // URL에서 한번에 읽어올 데이터 양
			if(totalCount==0) return;
			File file = null;
			for(int i=1;i<=((totalCount%max==0)?totalCount/max:totalCount/max+1);i++) {
				String strUrl =	"http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
						+ "bgnde=" +bgnde
						+ "&endde=" +endde
    					+ "&pageNo="+ i
						+ "&numOfRows=" + max
						+ "&ServiceKey=O2GKwCxkLW84OZXlbc7wuiJE8cAieTSwRG%2FVOe3KQVKnFOoOpxqzYt7CsWezKxah0HuRCGpKAMj%2FqA7lhOG0Vg%3D%3D";
				URL url;
					url = new URL(strUrl);
				InputStream is = url.openStream();
				Scanner sc = new Scanner(is);
				
				String path = System.getProperty("user.dir");
				
				file = new File(path+"\\Protect","protect_"+bgnde+"_"+i+".xml");
				PrintWriter pw = new PrintWriter(file);
		
				while(sc.hasNext()) {    
				   String str = sc.nextLine();
				   pw.append(str);
				   pw.flush(); // 항상 해줘야 한다.
			    }
		        sc.close();
			}
	        System.out.println(file.exists()?file.getAbsolutePath():"");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
	
	//오늘 데이터만 추가하기
	public void insertList(String bgnde) {
		int totalCount, max = 50;
		try {
			totalCount = getJsonTotalCount(bgnde, bgnde);
			if(totalCount==0) return;
			for(int i=1;i<=((totalCount%max==0)?totalCount/max:totalCount/max+1);i++) {
				String strUrl =	"http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
						+ "bgnde=" +bgnde
						+ "&endde=" +bgnde
						+ "&pageNo="+ i
						+ "&numOfRows="+max
						+ "&ServiceKey=O2GKwCxkLW84OZXlbc7wuiJE8cAieTSwRG%2FVOe3KQVKnFOoOpxqzYt7CsWezKxah0HuRCGpKAMj%2FqA7lhOG0Vg%3D%3D";
				HttpURLConnection conn = (HttpURLConnection) new URL(strUrl).openConnection();
		        conn.connect();
		        
		        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
		        StringBuffer st = new StringBuffer();
		        String line;
		        
		        while ((line = reader.readLine()) != null) {    
		        	st.append(line);
		        }
		     
		        JSONObject xmlJSONObj = XML.toJSONObject(st.toString());
		        String jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
		        
		        //System.out.println(xmlJSONObj.get("response"));
		        JSONObject job = (JSONObject) xmlJSONObj.get("response");
		        //System.out.println(job.get("body").toString());
		        
		        JSONObject j2 = (JSONObject) job.get("body");
		        JSONObject j3 = (JSONObject) j2.get("items");
		        JSONArray jsonArray = j3.getJSONArray("item");
		        
		        for(int j=0;j<jsonArray.length();j++) {
		        	ProtectVO vo = new ProtectVO();
		        	JSONObject o = (JSONObject) jsonArray.get(j);
		        	vo.setAge((o.has("age"))?o.get("age").toString():"");
		        	vo.setCareAddr((o.has("careAddr"))?o.get("careAddr").toString():"");
		        	vo.setCareNm((o.has("careNm"))?o.get("careNm").toString():"");
		        	vo.setCareTel((o.has("careTel"))?o.get("careTel").toString():"");
		        	vo.setChargeNm((o.has("chargeNm"))?o.get("chargeNm").toString():"");
		        	vo.setColorCd((o.has("colorCd"))?o.get("colorCd").toString():"");
		        	vo.setDesertionNo((o.has("desertionNo"))?o.get("desertionNo").toString():"");
		        	vo.setFilename((o.has("filename"))?o.get("filename").toString():"");
		        	vo.setHappenDt((o.has("happenDt"))?o.get("happenDt").toString():"");
		        	vo.setHappenPlace((o.has("happenPlace"))?o.get("happenPlace").toString():"");
		        	vo.setKindCd((o.has("kindCd"))?o.get("kindCd").toString():"");
		        	vo.setNeuterYn((o.has("neuterYn"))?o.get("neuterYn").toString():"");
		        	vo.setNoticeEdt((o.has("noticeEdt"))?o.get("noticeEdt").toString():"");
		        	vo.setNoticeNo((o.has("noticeNo"))?o.get("noticeNo").toString():"");
		        	vo.setNoticeSdt((o.has("noticeSdt"))?o.get("noticeSdt").toString():"");
		        	vo.setOfficetel((o.has("officetel"))?o.get("officetel").toString():"");
		        	vo.setOrgNm((o.has("orgNm"))?o.get("orgNm").toString():"");
		        	vo.setPopfile((o.has("popfile"))?o.get("popfile").toString():"");
		        	vo.setProcessState((o.has("processState"))?o.get("processState").toString():"");
		        	vo.setSexCd((o.has("sexCd"))?o.get("sexCd").toString():"");
		        	vo.setSpecialMark((o.has("specialMark"))?o.get("specialMark").toString():"");
		        	vo.setWeight((o.has("weight"))?o.get("weight").toString():"");
					protectDAO.insert(vo);
		        }  
		        System.out.println("ProtectService insertlist: protect_"+bgnde+"_"+i+" 번째 데이터 추가 완료");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
