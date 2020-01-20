<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보여주기</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/comm.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/board_style.css" rel="stylesheet">
	
<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/lang/summernote-ko-KR.js"></script>
<script type="text/javascript">
	$(function(){
	});
</script>
</head>
<body>
	<form action="${pageContext.request.contextPath }/board/updateForm"  method="post" >
	<div class="form_01" style="margin: 5%">
		<input type="hidden" name="p" value="${currentPage }">
		<input type="hidden" name="s" value="${pageSize }">
		<input type="hidden" name="b" value="${blockSize }">
		<input type="hidden" name="idx" value="${vo.content_idx }">
		<h2>${vo.subject }</h2>
		<div class="profile_img"><img src="${pageContext.request.contextPath }/resources/images/no_profile.gif"></div>
		<div class="profile_info">
			<span class="u_name" id="u_name${vo.content_idx }">${vo.u_name }</span>
			<span id="content_regdate${vo.content_idx }" class="content_regdate">
				<fmt:formatDate value="${vo.content_regdate }" pattern="yy-MM-dd hh:mm:ss"/>
			</span>
		</div>
		
		<div id="top_bnt"  class="btn_confirm write_div">
		<c:if test="${not empty sessionScope.vo }">
			<c:if test="${sessionScope.vo.u_id==vo.u_id}">
				<button type='button' onclick='sendPost("boardList",{"idx":"${vo.content_idx }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});' class='btn btn-default'>취소</button>
				<button type='button'  id='btn_delete'  class='btn btn-danger' onclick='sendPost("deleteOk",{"idx":"${vo.content_idx }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});'>삭제</button>
				<button type='submit'  id='btn_update'  class='btn btn-success'>수정</button>
			</c:if>
			<c:if test="${sessionScope.vo.u_id!=vo.u_id}">
				<button type='button' onclick='sendPost("boardList",{"idx":"${vo.content_idx }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});' class='btn btn-default'>목록보기</button>
			</c:if>
		</c:if>
		<c:if test="${empty sessionScope.vo }">
			<button type='button' onclick='sendPost("boardList",{"idx":"${vo.content_idx }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});' class='btn btn-default'>목록보기</button>
		</c:if>
		</div>
		
		<hr>
		
		<div>
			<div class="content">${vo.content }</div>
		</div>
	</div>		
	</form>

</body>
</html>