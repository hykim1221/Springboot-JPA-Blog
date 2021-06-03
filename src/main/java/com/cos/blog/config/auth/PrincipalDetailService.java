package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // 이때 bean등록이 된다
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	// 스프링이 로그인요청을 가로챌때, username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 함.
	// username이 DB에 있는지만 확인해주면됨. 그것을 밑의 함수가 해줌
	
	// 로그인이 진행될때, loaduserByUsername함수가 자동으로 실행이 되면서 findByUsername의 username을 찾고
	// 찾을수 없으면 orElseThrow가 실행되고 password가 틀렸으면 password는 알아서 처리해줌
	// 맞으면 User principal이 리턴이 되고 PrincipalDetail(principal)에 담아주고 PrincipalDetail을 리턴해준다.
	// 이때 시큐리티의 세션에 유저정보가 저장이 된다. 그때 타입이 UserDetails에 있는 타입이여야한다.
	// 이것을 만들어야만 우리가 커스터마이징한 user정보를 담아서 리턴해줄수 있다.(밑에걸 안하면 기본아이디는 user고 패스워드는 콘솔창것만 쓸수있다)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당사용자를 찾을 수 없습니다. : "+username);
				});
		return new PrincipalDetail(principal);
	}
}
