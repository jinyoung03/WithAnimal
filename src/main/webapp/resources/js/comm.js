/**
 * 스크립트 기반 POST 전송
 * @param action - URL
 * @param params - JSON
 * @returns
 * 호출 : sendPost('list.jsp',{"p":1,"b":10,"s":10});
 */
function sendPost(action, params) {
	var form = document.createElement('form'); // 폼작성
	form.setAttribute('method', 'post'); // 메서드를 post로 만듬
	form.setAttribute('action', action); // 액션 지정
	document.charset = "utf-8";
	for ( var key in params) {
		// 히든 필드를 작성
		var hiddenField = document.createElement('input');
		hiddenField.setAttribute('type', 'hidden');
		hiddenField.setAttribute('name', key);
		hiddenField.setAttribute('value', params[key]);
		// 폼에 추가
		form.appendChild(hiddenField);
	}
	// 폼을 body에 추가
	document.body.appendChild(form);
	// 전송
	form.submit();
}