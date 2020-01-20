<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>보호동물</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script> 
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/comm.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/protectResult_style.css" rel="stylesheet">
	
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
 <section class="container">
    <div class="row no-gutters justify-content-between">
    	<input type="hidden" name="p" value="${currentPage }">
		<input type="hidden" name="s" value="${pageSize }">
		<input type="hidden" name="b" value="${blockSize }">
		<input type="hidden" name="idx" value="${vo.content_idx }">
        <div class="col-lg-82-percnet g-mt-10 g-mb-20 g-pr-30--lg">
            <div class="row g-pt-20">
                <div class="col-md-5">
                	<table>
                    <ul class="list-inline g-font-size-16">
                        <li class="list-inline-item g-mx-3 font-weight-bold">
							<span class="u-label u-label--lg g-bg-primary g-rounded-20 g-px-10">${vo.processState }</span></li> <!--상태:보호중-->
                        <li class="list-inline-item g-mx-3 font-weight-bold">${vo.kindCd }</li> <!-- 품종 -->
                    </ul>
					<div>
						<img id="popfile${vo.content_idx }" src="${vo.popfile}" alt="${vo.kindCd }">
					</div>
                <!-- <div class="record-main-slider">
                        <ul class="bxslider d-none g-pa-0 text-center">
							<li><img id="popfile${vo.content_idx }" src="${vo.popfile}" alt="${vo.kindCd }"></li> 
						</ul>
                    </div> -->
          			</table>
                </div>
                <div class="col-md-7">
                	<table>
                    <ul class="g-pt-0 g-pt-30--lg g-font-size-16">
                        <li class="g-py-3 g-py-10--lg"><span class="font-weight-bold d-inline-block g-width-120">공고번호</span>${vo.noticeNo }</li>
                        <li class="g-py-3 g-py-10--lg"><span class="font-weight-bold d-inline-block g-width-120">발견장소</span>${vo.happenPlace }</li>
                        <li class="g-py-3 g-py-10--lg"><span class="font-weight-bold d-inline-block g-width-120">종</span>${vo.kindCd }</li>
                        <li class="g-py-3 g-py-10--lg"><span class="font-weight-bold d-inline-block g-width-120">나이</span>${vo.age }</li>
                        <li class="g-py-3 g-py-10--lg"><span class="font-weight-bold d-inline-block g-width-120">성별 / 몸무게</span> ${vo.sexCd } / ${vo.weight }</li>
                    </ul>
                    </table>
                </div>
                	<div id="top_bnt"  class="btn_confirm write_div">
						<button type='button' onclick='sendPost("protectResult",{"idx":"${vo.content_idx-1 }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});' class='btn btn-default'>이전</button>
					</div>
                	<div id="top_bnt"  class="btn_confirm write_div">
						<button type='button' onclick='sendPost("protectList",{"idx":"${vo.content_idx }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});' class='btn btn-default'>목록보기</button>
					</div>
					<div id="top_bnt"  class="btn_confirm write_div">
						<button type='button' onclick='sendPost("protectResult",{"idx":"${vo.content_idx+1 }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});' class='btn btn-default'>다음</button>
					</div>
            </div>
		</div>
 </section>

</body>
</html>