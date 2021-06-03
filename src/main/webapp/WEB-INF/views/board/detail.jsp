<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../rayout/header.jsp"%>
<link href="/css/detail.css" rel="stylesheet">

<div class="container">
	<br />
	<div class="index--title">
		<div class="index--title-text">글 내 용</div>
		<div class="index--write">
			<c:if test="${board.user.id == principal.user.id}">
				<a id="btn-delete" class="index--write-d" style="background-color: white !important; color: #C65FF9 !important; border: 1px solid #C65FF9">삭제</a>
				<a href="/board/${board.id}/updateForm" class="index--write-u">수정</a>
			</c:if>
		</div>
	</div>

	<!-- 	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button> -->
	<br />
	<div class="detail--content">
		<ul>
			<li class="detail--content-title">
				<div class="detail--content-title-1">No.</div>
				<div id="id" class="detail--content-title-2">${board.id }</div>
				<div class="detail--content-title-3">작성자</div>
				<div class="detail--content-title-4">${board.user.username }</div>
			</li>
			<li class="detail--content-title">
				<div class="detail--content-title-5">제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목</div>
				<div class="detail--content-title-6">${board.title}</div>
			</li>
			<li class="detail--content-main">
				<div>
					<div>${board.content}</div>
				</div>
			</li>
		</ul>
	</div>
	<div class="detail--reply-title">
		<div class="detail--reply-title-1">Comment</div>
		<div class="detail--reply-title-2">&nbsp;</div>
	</div>
	<div>
		<form>
			<input type="hidden" id="userId" value="${principal.user.id}" /> <input type="hidden" id="boardId" value="${board.id}" />
			<div class="detail--reply-text">
				<textarea id="reply-content" class="detail--reply-textarea" style="width: 100%; height: 120px;" rows="1"></textarea>
			</div>
			<div class="detail--reply-btn">
				<button type="button" id="btn-reply-save" class="detail--reply-button">등록</button>
			</div>
		</form>
	</div>
	<div>
		<div class="detail--replylist">CommentList</div>
		<ul id="reply-box" class="detail--replylist-list">
			<c:forEach var="reply" items="${board.replys}">
				<li id="reply-${reply.id}">
				<div class="detail--replylist-detail">
					<div class="detail--replylist-detail-1">
						${reply.user.username}
					</div>
					<div class="detail--replylist-detail-2">${reply.content}</div>
					<div class="detail--replylist-detail-3">
						<c:if test="${reply.user.id == principal.user.id}">
							<button onClick="index.replyDelete(${board.id}, ${reply.id})" class="detail--replylist-delete">삭제</button>
						</c:if>
					</div>
				</div>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
<script src="/js/board.js"></script>
<%@ include file="../rayout/footer.jsp"%>

