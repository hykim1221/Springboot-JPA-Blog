package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> JAVA(다른언어) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor // 빈 생성자
@AllArgsConstructor // 전체 생성자
@Builder // builder 패턴
@Entity // User 클래스가 MySQL에 테이블이 생성된다. 
// @DynamicInsert // insert시에 null인 필드를 제외시켜준다
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. 오라클에서는 시퀀스, mysql에서는 auto_increment
	private int id; // 오라클에서는 시퀀스, mysql에서는 auto_increment의 역할을 담당, 비워놔도 자동으로 들어감
	
	@Column(nullable = false, length = 100, unique = true) // null이 될수 없고 길이는 30자제한
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬 (비밀번호 암호화하기위해 넉넉하게) 
	private String password; 
	
	@Column(nullable = false, length = 50)
	private String email;
	
	// @ColumnDefault("user")
	// DB는 RoleType라는게 없다. Enum string이라고 알려줘야한다.
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다.(오타방지를 위함) ADMIN, USER 중 3개만 넣을 수 있도록 할 수 있기때문
	
	private String oauth; // kakao, google
	
	
	@CreationTimestamp // 시간이 자동으로 입력
	private Timestamp createDate; //sql의 Timestamp, 비워놔도 자동으로 들어감
}
