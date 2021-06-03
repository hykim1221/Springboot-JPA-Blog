package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.model.User;

// bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는것
// 3개는 거의 세트임
@Configuration // bean등록 (IOC관리)
@EnableWebSecurity // 시큐리티라는 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired // DI한다
	private PrincipalDetailService principalDetailService;
	
	//Ait+shift+s해서 override/implement Methods를 클릭해서 authenticationManagerBean을 등록
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	@Bean // IOC가 된다. 개발자가 아닌 스프링이 관리하게 된다.
	public BCryptPasswordEncoder encodePWD() {
		// String encPassword = new BCryptPasswordEncoder().encode("1234"); // 1234를 암호화 해서 넣어준다 예시 test패키지의 EncTest에 있음
		return new BCryptPasswordEncoder();
	}
	

	// 시큐리티가 대신 로그인해주는데 password를 가로채기하는데
	// 해당 password가 뭘로 해쉬가 되서 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교할 수 있음.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf -> 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") // 3. 이 주소가 포함이 안된 모든페이지에 대한 요청은 로그인폼으로 가게 됨
				.permitAll()
				.anyRequest() // 1. 인증(로그인)이 되지않은 어떠한 요청은
				.authenticated()
		.and()
			.formLogin()
			.loginPage("/auth/loginForm") // 2. 로그인 폼으로 옴 4. 로그인 폼으로 옴
			.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 로그인을 가로채서 대신 로그인해준다 5. 해당 로그인 폼으로 옴
			.defaultSuccessUrl("/"); // 로그인이 끝나면 해당주소로 이동 6. 정상적으로 로그인이 될때 이곳으로

	}
}

// 로그인 과정 총 정리 (홈페이지에서 로그인 요청이 왔을 시)
// 1. 로그인요청이 오는순간 SecurityConfig.java의 configure매소드의 .loginProcessingUrl("/auth/loginProc")에서 가로챈다.
// 2. 가로챈 아이디와 비밀번호를 PrincipalDetailService.java의 loadUserByUsername메소드로 던진다.
// 3. 던져서 loadUserByUsername의 String username에 쏙 들어가서 메소드실행문에서 
//     User principal = userRepository.findByUsername(username)가 데이터베이스에서 아이디를 찾아서 principal로 넣어주고,
//     return new PrincipalDetail(principal)의 PrincipalDetail에 담아서 리턴을 해주는데 리턴을 할때,
// 4. 우리가 등록해둔 SecurityConfig.java의 37번째줄 매소드의 auth.userDetailsService를 통해서 principalDetailService이 로그인 요청을 하고,
//     return new PrincipalDetail(principal)이 리턴이 되고
// 5. SecurityConfig.java의 37번째줄 매소드의passwordEncoder로 해서 사용자가 적은 password를 encodePWD()에 넣어 암호화를 하고
//		DB에 비교를 한다. 그 비교가 끝나서 둘다 정상인것을 확인하면
// 6. 4번의 PrincipalDetail에 유저정보가 저장이 된다.
// 7. 로그인이 진행되면 SecurityConfig.java의 54번째줄 .defaultSuccessUrl("/");가 실행이 되는데 "/"가 어딨냐면
// 		BoardContriller.java의 index()로 이동이 되는데 이때는 섹션이 만들어져 있음.
// 8. 만들어진 섹션은 컨트롤러에서 섹션을 어떻게 찾냐면