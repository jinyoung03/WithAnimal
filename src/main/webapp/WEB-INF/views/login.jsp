<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1,initial-scale=1,user-scalable=1" />
	<title>회원관리 - 로그인</title>
	<!-- Custom CSS -->
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/resources/css/login_style.css">
	<!-- Google Font -->
	<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean" rel="stylesheet" type="text/css">
	<!--<link href="http://fonts.googleapis.com/css?family=Lato:100italic,100,300italic,300,400italic,400,700italic,700,900italic,900" rel="stylesheet" type="text/css">-->
    <!-- Bootstrap Core CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!-- jQuery Library -->
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.0/jquery.min.js"></script>
    <!-- Bootstrap Core JS -->
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	function fromCheck() {
		var data=$("#u_id").val();
		if(data==null || data.trim().length==0){
			alert("아이디는 반드시 입력해주세요.");
			$("#u_id").val("");
			$("#u_id").focus();
			return false;
		}
		if(!checkEmail(data)){
			alert("아이디는 이메일을 사용합니다.");
			$("#u_id").val("");
			$("#u_id").focus();
			return false;
		}
		var data = $("#u_pw").val();
		if(data==null || data.trim().length==0){
			alert("비밀번호를 입력해주세요.");
				$("#u_pw").val("");
				$("#u_pw").focus();
				return false;
			}
			return true;
		}
		function checkEmail(email){
			var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
			return re.test(email);
		}


	</script>
</head>
<body>
		<section class="container">
	    <section class="login-form">
		<form role="login" action="${pageContext.request.contextPath }/loginOk" method="post" onsubmit="return fromCheck()">
			<section>
				<a href="${pageContext.request.contextPath }/" >
					<img src="${pageContext.request.contextPath }/resources/images/logo.png" alt="이미지"/>
				</a>
			</section>
			
			<label>이메일</label>			
			<input type="text" name="u_id" required class="form-control" id="u_id" value="${u_id}"/>
			
			<label>비밀번호</label>
			<input type="password" name="u_pw" required class="form-control" id="u_pw" value="${u_pw }"/>
			
			<button type="submit" name="login" class="btn btn-success btn-block">로그인</button>
			
			
			<div>
				<input type="checkbox" name="remember" value="save" ${not empty u_id ? "checked='checked'":"" } />&nbsp;&nbsp;아이디 저장
				<a href="${pageContext.request.contextPath }/join" class="pull-right">회원가입</a>
			</div>		
			
			<div style="margin-top: 4px;">
				<a href="${pageContext.request.contextPath }/idSearch" class="pull-left" >아이디 찾기</a>
				<a href="${pageContext.request.contextPath }/pwSearch" class="pull-right">비밀번호 찾기</a>
			</div>	
		</form>
		</section>
	</section>
</body>
</html>