<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../rayout/header.jsp"%>

<div class="container">
	<br />
	<div class="index--title">
		<div class="index--title-text">글 쓰 기</div>
		<div class="index--write">
			<a id="btn-save">작성완료</a>
		</div>
	</div>
	<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter Title" id="title">
		</div>

		<div class="form-group">
			<textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>

	</form>
</div>

<script>
	$('.summernote').summernote({
		tabsize : 2,
		height : 300
	});
</script>
<script src="/js/board.js"></script>
<%@ include file="../rayout/footer.jsp"%>

