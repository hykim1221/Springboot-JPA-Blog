package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //스프링이 com.cos.blog패키지의 특정 어노테이션이 붙어있는 클래스파일들을 new해서 스프링 컨테니어에 관리해준다.
public class BlogControllerTest {

	//http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>HELLO HELLO</h1>";
	}
	
}
