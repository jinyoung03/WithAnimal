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

  <title>반려동물 분실등록 게시판</title>

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

  <!-- Custom styles for this template <link href="${pageContext.request.contextPath}/resources/css/list_style.css" rel="stylesheet">-->
  <link href="${pageContext.request.contextPath }/resources/css/agency.min.css" rel="stylesheet">
  
</head>
	<script type="text/javascript">
		function formInput(){
			location.href='boardForm';
		}
	</script>

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
  <header class="listhead">
    <div class="container">
      <div class="intro-text">
        <div class="intro-lead-in"> 반려동물을 보호중이거나 목격하신 분은 꼭 제보 부탁드립니다!</div>
        <div class="intro-heading text-uppercase">반려동물 분실등록 게시판</div>
        <c:if test="${not empty sessionScope.vo}">
	        <a class="btn btn-primary btn-xl text-uppercase js-scroll-trigger" onclick="formInput()" id="submitBtn">분실 등록 &raquo;</a>
	    </c:if>
      </div>
    </div>
  </header>
    
  <!-- section  -->
  <section class="page-section" id="boardList">
    <div class="container">
      <div class="row">
      
        <div class="col-lg-12 text-center">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>번호</th>
						<th width="50%">제목</th>
						<th>작성자</th>
						<th>작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${paging.totalCount==0 }">
						<tr>
							<td colspan="4" align="center">작성된 글이 없습니다.</td>
		                </tr>               
					</c:if>
					
					<c:if test="${paging.totalCount!=0 }">
					<c:set var="no" value="${paging.totalCount-(paging.currentPage-1)*paging.pageSize }"/>
					<c:forEach var="vo" items="${paging.list }" varStatus="vs">
						<tr align="center">
							<td id="idx${vo.content_idx }">${vo.content_idx }</td>
							<td id='subject${vo.content_idx }' style="text-align: left;">
								<span onclick='sendPost("boardResult",{"p":${paging.currentPage},"b":${paging.blockSize },"s":${paging.pageSize },"idx":${vo.content_idx }});' style="cursor: pointer;">
									<c:out value="${vo.subject }"/>
								</span>
							</td>
							<td id="u_name${vo.content_idx }">${vo.u_name }</td>
							<td id="content_regdate${vo.content_idx }"><fmt:formatDate value="${vo.content_regdate }" pattern="yy-MM-dd hh:mm:ss"/></td>
						</tr>
					</c:forEach> 
					</c:if> 
					
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
						<a class='page-link' href='javascript:sendPost("?",{"p":"${paging.startPage-1}","s":"${paging.pageSize}","b":"${paging.blockSize}"});' aria-label='Previous'>
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
			    		<li class='page-item'><a class='page-link' href='javascript:sendPost("?",{"p":"${i}","s":"${paging.pageSize}","b":"${paging.blockSize}"});'>${i}</a></li>
			    	</c:otherwise>
			    	</c:choose>
			    </c:forEach>
	
				<c:if test="${paging.endPage<paging.totalPage }">
					<li class='page-item'>
					<a class='page-link' href='javascript:sendPost("?",{"p":"${paging.endPage+1}","s":"${paging.pageSize}","b":"${paging.blockSize}"});' aria-label='Next'>
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