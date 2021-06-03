<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../rayout/header.jsp"%>

<div class="container">
	<br />
	<div class="index--title">
		<div class="index--title-text">로 그 인</div>
	</div>
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label> <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=a10ba274ea28f559de3ac7a191a2f4f1&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px"
			src="/image/kakao_login_button.png" /></a>
	</form>
</div>

<%@ include file="../rayout/footer.jsp"%>

