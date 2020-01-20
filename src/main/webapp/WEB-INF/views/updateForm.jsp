<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<!-- include libraries(jQuery, bootstrap) -->
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/comm.js"></script>

<!-- include summernote css/js -->
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/lang/summernote-ko-KR.js"></script>
<script type="text/javascript">
	$(function() {
		$('#summernote').summernote(
				{
					lang : 'ko-KR', // default: 'en-US'
					height : 300, // set editor height
					minHeight : null, // set minimum height of editor
					maxHeight : null, // set maximum height of editor
					fontNames : [ '맑은고딕', 'Arial', 'Arial Black',
							'Comic Sans MS', 'Courier New', ],
					fontNamesIgnoreCheck : [ '맑은고딕' ],
					focus : true,
					callbacks : {
						onImageUpload : function(files, editor, welEditable) {
							for (var i = files.length - 1; i >= 0; i--) {
								sendFile(files[i], this);
							}
						}
					}
				});
	});
	function sendFile(file, el) {
		var form_data = new FormData();
      	form_data.append('file', file);
      	$.ajax({
        	data: form_data,
        	type: "POST",
        	url: 'profileImage',
        	cache: false,
        	contentType: false,
        	enctype: 'multipart/form-data',
        	processData: false,
        	success: function(img_name) {
          		$(el).summernote('editor.insertImage', img_name);
        	}
      	});
    }
</script>
</head>
<body>
	<form action="${pageContext.request.contextPath }/board/updateOk" method="post">
	<div class="form_01" style="margin: 5%">
		<input type="hidden" name="p" value="${currentPage }">
		<input type="hidden" name="s" value="${pageSize }">
		<input type="hidden" name="b" value="${blockSize }">
		<input type="hidden" name="idx" value="${vo.content_idx }">
		<h2>게시글 수정</h2>
			<div style="margin-top:30px; margin-bottom:15px;">		
				<input type="text" class="form-control" name="subject" id="subject" value="${vo.subject }" required="required" />
			</div>
		
			<textarea id="summernote" name="content">${vo.content }</textarea>
				
			<div class="btn_confirm write_div">
			  	<button type="button" class="btn btn-default" onclick='sendPost("boardResult",{"idx":"${vo.content_idx }","p":"${currentPage }","b":"${blockSize }","s":"${pageSize }"});'>취소</button>
				<button type="submit" id="btn_submit" class="btn btn-success">수정</button>
			</div>
	</div>	
	</form>
</body>
</html>




















