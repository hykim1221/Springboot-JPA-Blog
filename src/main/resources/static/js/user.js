let index = {
	init: function() {
		$("#btn-save").on("click", () => { // function() {}를 사용안하고 ()=>{}사용하는이유 > this를 바인딩하기 위해서
			this.save();
		});

		$("#btn-update").on("click", () => { // function() {}를 사용안하고 ()=>{}사용하는이유 > this를 바인딩하기 위해서
			this.update();
		});
	},



	// 회원가입
	save: function() {
		// alert('user의 save함수 호출됨');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		// console.log(data);

		// ajax호출시 default가 비동기호출
		// ajax통신을 통해서 3개의 데이터를 json으로 변경하여 insert요청!!
		// ajax가 통신을성공하고 서버가 json을 리턴을 하면 자동으로 자바 오브젝트로 변환
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤타입인지
			dataType: "json" // 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String(생긴게 json이라면 => javascript로 변경해줌), 생략가능

			// 성공했을때
		}).done(function(resp) {
			if (resp.status === 500) {
				alert("회원가입이 실패하였습니다.");
			} else {
				location.href = "/";
				alert("회원가입이 완료되었습니다.");
			}

			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

	// 회원수정
	update: function() {

		let data = {
			id: $("#id").val(), // 어떠한 아이디인지 알아야 하기 때문에, username: $("#username").val() => username은 수정하지 않을것이기에 필요없다.
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};

		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), // http body데이터
			contentType: "application/json; charset=utf-8", // body데이터가 어떤타입인지
			dataType: "json" // 요청을 서버로해서 응답이 왔을때 기본적으로 모든것이 String(생긴게 json이라면 => javascript로 변경해줌), 생략가능

			// 성공했을때
		}).done(function(resp) {
			alert("회원수정이 완료되었습니다.");
			location.href = "/";

			// 실패했을때
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},

}

index.init();