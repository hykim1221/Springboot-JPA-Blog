let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});

	},


	// 글쓰기
	save: function() {
		// alert('user의 save함수 호출됨');
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
			// 성공했을때
		}).done(function(resp) {
			alert("글쓰기가 완료되었습니다.");
			location.href = "/";
			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},


	// 글삭제하기
	deleteById: function() {
		let id = $("#id").text();

		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json"
			// 성공했을때
		}).done(function(resp) {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},


	// 글수정하기
	update: function() {
		let id = $("#id").val();

		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "PUT",
			url: "/api/board/" + id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
			// 성공했을때
		}).done(function(resp) {
			alert("글수정이 완료되었습니다.");
			location.href = "/";
			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	replySave: function() {
		// alert('user의 save함수 호출됨');
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};
		
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
			// 성공했을때
		}).done(function(resp) {
			alert("댓글작성이 완료되었습니다.");
			location.href = `/board/${data.boardId}`;
			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
		replyDelete: function(boardId, replyId) {
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
			// 성공했을때
		}).done(function(resp) {
			alert("댓글삭제가 완료되었습니다.");
			location.href = `/board/${boardId}`;
			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
}

index.init();