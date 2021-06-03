<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!-- 현재 세션에 저장된 로그인된 객체가 principal란 이름으로 저장이 된다.
		이것은 principalDetail.java와 연결이 되어있다. (세션값임)-->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
<title>블로그 만들기</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
<link href="/css/header.css" rel="stylesheet">
</head>
<body>
	<nav class="header--container">
		<div class="header--login">
			<div class="header--login-block1">
				<a class="header--title" href="/"><img src="/image/blog_title.png"></a>
			</div>
			<div class="header--login-block2">
				<c:choose>
					<c:when test="${empty principal}">
						<h5 class="header--login-info">&nbsp;</h5>
						<ul class="header--login-box">
							<li class="header--login-text"><button class="header--login-text-1">
									<a href="/auth/loginForm">로 그 인</a>
								</button></li>
							<li class="header--login-text"><button class="header--login-text-2">
									<a href="/auth/joinForm">회원가입</a>
								</button></li>
						</ul>
					</c:when>
					<c:otherwise>
						<h5 class="header--login-info"><a>${principal.user.username}</a> 님 환영합니다!</h5>
						<ul class="header--login-box">
							<li class="header--login-text"><button class="header--login-text-1"><a href="/user/updateForm">회원정보</a></button></li>
							<li class="header--login-text"><button class="header--login-text-2"><a href="/logout">로그아웃</a></button></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</nav>
	<br />