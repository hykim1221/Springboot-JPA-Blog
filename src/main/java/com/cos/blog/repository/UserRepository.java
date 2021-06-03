package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

// DAO역할
// 자동으로 bean등록이 된다.
// @Repository는 생략이 가능하다.
//											해당 jpa레파지토리는 		user테이블이 관리하는 레파지토리, 그리고 이 user테이블의 프라이머리키는 integer(숫자)이다 
public interface UserRepository extends JpaRepository<User, Integer>{
	
	
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);

}


// 로그인페이지에 필요
// JPA Naming쿼리 ,네이밍전략 
// SELECT * FROM user WHERE username = ? AND password = ?; 와 같은 뜻
// User findByUsernameAndPassword(String username, String password);

// 위의 함수와 같은뜻
// @Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
// User login(String username, String password)