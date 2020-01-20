package kr.withanimal.board.vo;

import java.util.List;

public class Paging<T> {
	// 입력받을 변수 4개
	private int totalCount; 	// 전체 개수
	private int currentPage;	// 현재 페이지
	private int pageSize;		// 페이지당 글수
	private int blockSize; 		// 하단표시 페이지 갯수
	
	
	// 계산할 변수 5개
	private int totalPage;		// 전체 페이지 수
	private int startNo;		// 시작 글번호
	private int endNo;			// 끝 글번호
	private int startPage;		// 시작 페이지 번호
	private int endPage;		// 끝 페이지 번호
	
	
	// 1페이지 분량의 VO데이터를 담을 변수
	private List<T> list;

	public Paging(int totalCount, int currentPage, int pageSize, int blockSize) {
		super();
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		calc();		
	}

	// 입력된 변수의 유효성을 검증
	private void calc() {
		if(currentPage<=0) currentPage=1;
		if(pageSize<1) pageSize=10;
		if(blockSize<1) blockSize=10;
		if(totalCount>0) {
			totalPage = (totalCount-1)/pageSize+1;
			if(currentPage>totalPage) currentPage=1;
			startNo=(currentPage-1)*pageSize+1;
			endNo=startNo+pageSize-1;
			if(endNo>totalCount) endNo= totalCount;
			startPage=(currentPage-1)/blockSize*blockSize+1;
			endPage=startPage+blockSize-1;
			if(endPage>totalPage) endPage = totalPage;			
		}		
	}


	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public int getPageSize() {
		return pageSize;
	}


	public int getBlockSize() {
		return blockSize;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public int getStartNo() {
		return startNo;
	}


	public int getEndNo() {
		return endNo;
	}


	public int getStartPage() {
		return startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	@Override
	public String toString() {
		return "Paging [totalCount=" + totalCount + ", currentPage=" + currentPage + ", pageSize=" + pageSize
				+ ", blockSize=" + blockSize + ", totalPage=" + totalPage + ", startNo=" + startNo + ", endNo=" + endNo
				+ ", startPage=" + startPage + ", endPage=" + endPage + ", list=" + list + "]";
	}	
}
