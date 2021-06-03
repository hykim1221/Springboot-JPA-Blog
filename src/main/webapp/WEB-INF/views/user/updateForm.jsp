<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../rayout/header.jsp"%>

<div class="container">
	<br />
	<div class="index--title">
		<div class="index--title-text">회 원 수 정</div>
	</div>
	<form>
		<input type="hidden" id="id" value="${principal.user.id}"/>
		<div class="form-group">
			<label for="username">Username (수정불가)</label> 
			<input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>
	
		<c:if test="${empty principal.user.oauth}">
		<div class="form-group">
			<label for="password">New Password</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		</c:if>
		
		<div class="form-group">
			<label for="email">Email</label> 
			<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
		</div>	
		

	</form>
		<button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>

<script src="/js/user.js"></script>
<%@ include file="../rayout/footer.jsp"%>

