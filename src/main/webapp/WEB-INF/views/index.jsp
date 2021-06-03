<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="rayout/header.jsp"%>
<link href="/css/index.css" rel="stylesheet">

<div class="container">
	<br />
	<div class="index--title">
		<div class="index--title-text">방 명 록</div>
		<div class="index--write">
			<a href="/board/saveForm">글쓰기</a>
		</div>
	</div>
	<div class="index--content">
		<ul>
			<li class="index--content-title">
				<div class="index--content-title-1">No.</div>
				<div class="index--content-title-2">제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</div>
				<div class="index--content-title-3">글 쓴 이</div>
			</li>
			<c:forEach var="board" items="${boards.content}">
				<li class="index--content-main">
					<div class="index--content-main-1">${board.id}</div>
					<div class="index--content-main-2">
						<div>
							<a href="/board/${board.id}">${board.title}</a>
						</div>
					</div>
					<div class="index--content-main-3">${board.user.username}</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<br />
	<div class="index--prenext">
		<ul class="pagination justify-content-center">
			<c:choose>
				<c:when test="${boards.first }">
					<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" style="color: white; background-color: #C65FF9; font-weight: 700;" href="?page=${boards.number-1}">Previous</a></li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${boards.last }">
					<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item"><a class="page-link" style="color: white; background-color: #C65FF9; font-weight: 700;" href="?page=${boards.number+1}">Next</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>


<%@ include file="rayout/footer.jsp"%>

