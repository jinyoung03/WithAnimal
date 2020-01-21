	<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>보호동물 공고 게시판</title>

  <!-- Bootstrap core CSS -->
  <link href="${pageContext.request.contextPath }/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="${pageContext.request.contextPath }/resources/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>
  <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,500,700,900&display=swap&subset=korean" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Oleo+Script+Swash+Caps&display=swap" rel="stylesheet">  

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath }/resources/css/agency.min.css" rel="stylesheet">
  
</head>

<body id="page-top">

  <!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand js-scroll-trigger" href="${pageContext.request.contextPath }/">With Animal</a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav text-uppercase ml-auto">
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="${pageContext.request.contextPath }/">HOME</a>
          </li>
          <li class="nav-item">
            <c:if test="${empty sessionScope.vo }">
              <a class="nav-link js-scroll-trigger"onclick="location.href='/member/login'">로그인</a>
            </c:if>
            <c:if test="${not empty sessionScope.vo }">
              <a class="nav-link js-scroll-trigger"onclick="location.href='/member/logout'">로그아웃</a>
            </c:if>
          </li>
          <c:if test="${empty sessionScope.vo }">
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="${pageContext.request.contextPath }/join">회원가입</a>
          </li>
          </c:if>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="${pageContext.request.contextPath }/board/protectList">보호동물 공고</a>
          </li>
          <li class="nav-item">
            <a class="nav-link js-scroll-trigger" href="${pageContext.request.contextPath }/board/boardList">반려동물 분실등록</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- Header -->
  <header class="protecthead">
    <div class="container">
      <div class="intro-text">
        <div class="intro-lead-in"> 보호중인 유기동물들의 자료를 확인할 수 있습니다.</div>
        <div class="intro-heading text-uppercase">보호동물 공고 게시판</div>
      </div>
    </div>
  </header>



  <!-- section  -->
  <section class="page-section" id="protectList">
    <div class="container">
      <div class="row">
      
        <div class="col-lg-12 text-center">
			<table class="table table-hover">
				<thead>
					<div >
					<ul class="list-inline">
						<li class="list-li-left">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","processState":"${processState}"});' class="g-text-underline-hover">전체</a>
						</li>
						<li class="list-li-left">|</li>
						<li class="list-li-left">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","kindCd":"dog","processState":"${processState}"});' class="g-text-underline-hover">개</a></li>
						<li class="list-li-left">|</li>
						<li class="list-li-left">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","kindCd":"cat","processState":"${processState}"});' class="g-text-underline-hover">고양이</a>
						</li>	
						<li class="list-li-left">|</li>
						<li class="list-li-left">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","kindCd":"etc","processState":"${processState}"});'  class="g-text-underline-hover">기타</a>
						</li>	
					</ul>
				</div>
				<div class="">
					<ul class="list-inline">
						<li class="list-li-right">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","kindCd":"${kindCd}","processState":"ongoing"});' class="g-text-underline-hover">보호중</a>
						</li>
						<li class="list-li-right">|</li>
						<li class="list-li-right">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","kindCd":"${kindCd}","processState":"end"});' class="g-text-underline-hover">종료</a>
						</li>
						<li class="list-li-right">|</li>
						<li class="list-li-right">
							<a href='javascript:sendPost("protectList",{"p":"1","bgnde":"${bgnde}","endde":"${endde}","kindCd":"${kindCd}"});' class="g-text-underline-hover">전체</a>
						</li>
					</ul>
				</div>	
				</thead>
				<tbody>
					<div class="row_content">
						<form action="protectList">
							<span>공고 시작일 : </span>
							<input type="text" id="bgnde" name="bgnde" value="${bgnde}" placeholder="검색시작일(20200101)">
							<input type="text" id="endde" name="endde" value="${endde}"  placeholder="검색종료일(20200131)">
							<input type="submit" id="btn_search" value="검색">
						</form>
					</div>
				</tbody>
				<tbody>
					<div class="">
						<c:if test="${paging.totalCount==0 }">
							<ul class="sub_photo">
								<li class="li-photo">
									<p class="thmb">게시된 글이 없습니다.</p>
								</li>
							</ul>
						</c:if>
						<c:if test="${paging.totalCount!=0 }">
							<div class="">
								<c:forEach var="vo" items="${paging.list }" varStatus="vs">
									<ul class="sub_photo">
										<c:if test="${not empty vo.desertionNo && not empty vo.popfile}">
										<li class="li-photo">
          								<a class="portfolio-link" data-toggle="modal" href="#portfolioModal${vo.content_idx }">
											<p class="thmb">
												<img id="popfile${vo.content_idx }" src="${vo.popfile}" alt="${vo.kindCd }" >
											</p>
											<p class="thmb_span" style="cursor: pointer;">
												<a href="#" class="btn btn-md u-btn-primary k-btn-hover g-rounded-50">${vo.processState }</a>
												<span title="${vo.kindCd }">
												<c:set var="noticeNo" value="${vo.noticeNo }"/>
													${fn:substring(noticeNo,3,5)}  
												<c:set var="vokindCd" value="${vo.kindCd }"/>
												<c:set var="vokindCd" value='${fn:split(vokindCd," ")[1]}'/>
												<c:set var="vokindCd" value='${fn:split(vokindCd,"-")[0]}'/>
													| ${vokindCd }
												</span>
											</p>
											</a>
											</li>
										</c:if>	
									</ul>
								</c:forEach>
							</div>
						</c:if>
					</div>
				</tbody>
			</table>
        </div>
        
        <div class="col-lg-12 text-center">
        <table style="margin: auto;">
	 	    <tr style="text-align: center;" onmouseover="this.style.backgroundColor='white'">
		      <td colspan="4">
			  <ul class="pagination" >
	  
				<c:if test="${paging.startPage>1 }">
					<li class='page-item'>
						<a class='page-link' href='javascript:sendPost("protectList",{"p":"${paging.startPage-1}","bgnde":"${bgnde}","endde":"${endde}","kindCd":"${kindCd}","processState":"${processState}"});' aria-label='Previous'>
						<span aria-hidden='true'>&laquo;</span>
						<span class='sr-only'>Previous</span>
						</a>
					</li>
				</c:if>
			
			    <c:forEach var="i" begin="${paging.startPage }" end="${paging.endPage }">
			    	<c:choose>
			    	<c:when test="${i==paging.currentPage }">
			    		<li class='page-item active'><span class='page-link'>${i}</span></li>
			    	</c:when>
			    	<c:otherwise>
			    		<li class='page-item'><a class='page-link' href='javascript:sendPost("protectList",{"p":"${i}","bgnde":"${bgnde}","endde":"${endde}","kindCd":"${kindCd}","processState":"${processState}"});'>${i}</a></li>
			    	</c:otherwise>
			    	</c:choose>
			    </c:forEach>
	
				<c:if test="${paging.endPage<paging.totalPage }">
					<li class='page-item'>
					<a class='page-link' href='javascript:sendPost("protectList",{"p":"${paging.endPage+1}","bgnde":"${bgnde}","endde":"${endde}","kindCd":"${kindCd}","processState":"${processState}"});' aria-label='Next'>
						<span aria-hidden='true'>&raquo;</span>
						<span class='sr-only'>Next</span>
					</a>
					</li>
				</c:if>
				
			  </ul>
			  </td>
			</tr>
		</table>
		</div>
        
      </div>
	</div>
  </section>
  <c:if test="${paging.totalCount!=0 }">
  <c:forEach var="vo" items="${paging.list }" varStatus="vs">
  <!-- Modal 6 -->
  <div class="portfolio-modal modal fade" id="portfolioModal${vo.content_idx }" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="close-modal" data-dismiss="modal">
          <div class="lr">
            <div class="rl"></div>
          </div>
        </div>
        <div class="container">
          <div class="row">
            <div class="col-lg-8 mx-auto">
              <div class="modal-body">
                <!-- Project Details Go Here -->
              <img id="popfile${vo.content_idx }" src="${vo.popfile}" alt="${vo.kindCd }" style="width: 80%"><br>
              <span class="font-weight-bold d-inline-block g-width-120">공고번호</span> ${vo.noticeNo } <br>
              <span class="font-weight-bold d-inline-block g-width-120">발견장소</span> ${vo.happenPlace } <br>
              <span class="font-weight-bold d-inline-block g-width-120">종</span> ${vo.kindCd } <br>
              <span class="font-weight-bold d-inline-block g-width-120">나이</span> ${vo.age } <br>
              <span class="font-weight-bold d-inline-block g-width-120">성별 / 몸무게</span> ${vo.sexCd } / ${vo.weight }<br>
              <span class="font-weight-bold d-inline-block g-width-120">특이사항 </span> ${vo.specialMark }<br><br> 
              <span class="font-weight-bold d-inline-block g-width-120">연락처 : ${vo.careNm } / ${vo.careTel }</span> 
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </c:forEach>
  </c:if>
		

  <!-- Footer -->
  <footer class="footer" style="background-color: #f8f9fa">
    <div class="container">
      <div class="row align-items-center">
        <div class="col-md-4">
          <span class="copyright">Copyright &copy; With Animal 2020</span>
        </div>
        <div class="col-md-4">
          <ul class="list-inline social-buttons">
            <li class="list-inline-item">
              <a href="#">
                <i class="fab fa-twitter"></i>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <i class="fab fa-facebook-f"></i>
              </a>
            </li>
            <li class="list-inline-item">
              <a href="#">
                <i class="fab fa-linkedin-in"></i>
              </a>
            </li>
          </ul>
        </div>
        <div class="col-md-4">
          <ul class="list-inline quicklinks">
            <li class="list-inline-item">
              <a href="http://act.animals.or.kr/cagefree/">케이지 프리 코리아</a>
            </li>
            <li class="list-inline-item">
              <a href="http://www.animal.go.kr/portal_rnl/index.jsp">동물보호관리시스템</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </footer>

	
  <!-- Bootstrap core JavaScript -->
  <script src="${pageContext.request.contextPath }/resources/vendor/jquery/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath }/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Plugin JavaScript -->
  <script src="${pageContext.request.contextPath }/resources/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Contact form JavaScript -->
  <script src="${pageContext.request.contextPath }/resources/js/jqBootstrapValidation.js"></script>
  <script src="${pageContext.request.contextPath }/resources/js/contact_me.js"></script>

  <!-- Custom scripts for this template -->
  <script src="${pageContext.request.contextPath }/resources/js/agency.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/comm.js"></script>
</body>

</html>		