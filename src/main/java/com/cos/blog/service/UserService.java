package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해줌. ioc를 해준다
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired // DI가 됨, 패스워드를 암호화 해주는 것
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		
		return user;
	}
	
	@Transactional // 트랜잭션들을 하나로 묶어줌, 밑에 하나라도 오류가 나면 전체 롤백시켜줌(ex 은행송금할때 송신자 수신자 둘중 하나라도 오류나면 안되기때문
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234원문
		String encPassword = encoder.encode(rawPassword); // 원문을 해쉬화
		user.setPassword(encPassword); // 해쉬화된 암호로 바꿔넣는다
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(User user) { // User는 외부에서 받는 user이다
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정해야한다.
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서이다.
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주기 때문이다.
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()-> {
					return new IllegalArgumentException("회원찾기실패");
				});
		// 찾았으면 persistance에 오브젝트가 들어올 것이다.
		
		// vaildata 체크 => oauth필드에 값이 없으면 수정가능
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword(); // 그리고 비밀번호를 받아서
			String encPassword = encoder.encode(rawPassword); // encoder함수에 패스워드를 넣어서 암호화 해서 받는다.
			persistance.setPassword(encPassword); 	// 그리고 패스워드를 수정해 주고
			persistance.setEmail(user.getEmail()); // 영속화 되어있는곳에서 email을 연걸해서 수정한다.			
		}
		
		// 위의 함수들 (회원수정 함수) 종료시 = 서비스종료가 됨 = 트랜잭션이 종료 = commit이 자동으로 됨.
		// 영속화 된 persistance 객체의 변화가 감지되면 (더티체킹이라고 함) update문을 날려줌.
	}

}
