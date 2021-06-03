package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다. (상속한 UserDetails의 PrincipalDetail에)
//															UserDetails에 있는것을 오버라이딩한다. => Alt+shift+s => override/implements Method => 전체체크확인후 ok
@Getter
public class PrincipalDetail implements UserDetails{
	
	private User user; // 콤포지션 (객체를 품고있는것)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() { // 
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	// 계정이 만료되지 않았는지 리턴한다 (true: 만료안됨, false: 만료됨) false면 로그인안됨
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있는지 안잠겨있는지 (true: 잠기지있음, false: 잠겨있음) false면 로그인안됨
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되었는지 (true: 만료안됨, fallse: 만료됨) false면 로그인안됨
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정활성화(사용가능)인지 아닌지 (true: 활성화되잇음)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 이 계정이 어떤권한을 가지고 있는지
	// 계정이 갖고있는 권한을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{return "ROLE_"+user.getRole();}); // 반드시 ROLE_USER와 같은형태의 결과값
		
		return collectors;
	}
	
	
}
