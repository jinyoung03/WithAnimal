<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<title>HOME</title>
</head>
<body>
	<h1> 
		동물 데이터 확인
	</h1> 
	<form action="saveXml">
		<input type="text" id="bgnde" name="bgnde" placeholder="20190101">
		<input type="text" id="endde" name="endde" placeholder="20190101">
		<input type="submit" value="XML로 저장하기"/>
	</form>
</body>
</html>
