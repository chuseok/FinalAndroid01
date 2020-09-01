<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<% request.setCharacterEncoding("UTF-8"); %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>validate</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="/resources/css/login.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="crossorigin="anonymous"></script>
<script src="/resources/jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="/resources/js/login.js"></script>

<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js" charset="utf-8"></script>

</head>
<body>

	<jsp:include page="./main/menu.jsp" />

	<div class="modal">
		<div class="modal-content">
			<div class="content-header">
				<div class="login">로그인</div>
				<div class="close">+</div>
			</div>

	<h2>
		<c:out value="${error}" />
	</h2>
	<h2>
		<c:out value="${logout}" />
	</h2>
						<form class="form" method="POST" action="/login" novalidate>
							<fieldset>
								<p class="clearfix">
									<label>아이디</label> <input type="text" id="Id" name="username"
										placeholder="영문,숫자 6~12자 입력하세요.">
								</p>

								<p class="clearfix">
									<label>비밀번호</label> <input type="password" id="password"
										name="password" placeholder="영문+숫자+특수문자 포함 8~16자로 입력해주세요.">
								</p>
								
								<input type="checkbox" name='remember-me'>아이디 저장
								

								<p class="clearfix">
									<input type="submit" value="로그인" class="button">
								</p>
								<p>
									아이디가 없으신가요?<a href="/main/join"> 여기를 눌러주세요</a>
								</p>

								아이디를 잊어버리셨나요? <a href="#">아이디 찾기</a>
<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							</fieldset>
						</form>
					<br>
<!-- 네이버 로그인 화면으로 이동 시키는 URL -->
<!-- 네이버 로그인 화면에서 ID, PW를 올바르게 입력하면 callback 메소드 실행 요청 -->
<div id="naver_id_login" style="text-align:center"><a href="${url}">
<img width="223" src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png"/></a></div>
<br>
		</div>
	</div>

	<Script>
		document.querySelector('.close').addEventListener('click', function() {
			document.querySelector('.modal').style.display = 'none';
		});
	</Script>
</body>
</html>