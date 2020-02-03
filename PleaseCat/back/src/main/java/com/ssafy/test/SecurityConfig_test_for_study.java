package com.ssafy.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

/*WebSecurityConfigureAdapter을 상속받은 시큐리티 설정 클래스를 선언한다.  
이 클래스에서는 유저 권한에 따른 URI 접근을 설정한다. 
예를들어 /admin 으로 시작하는 URI는 "ADMIN" 권한이 있는 유저만 접근 가능하고, 
인덱스 페이지나 회원 가입 페이지는 모든 사용자가 접근 가능하다.*/

/*Spring Security를 사용하기 위해서는 
Spring Security Filter Chain 을 사용한다는 것을 명시해 줘야 합니다. 
이것은 WebSecurityConfigurerAdapter를 상속받은 클래스에 
@EnableWebSecurity 어노테이션을 달아주면 해결됩니다. */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig_test_for_study {
/*
  

	// 만약 로그인 필요없이 특정 path에 접근하고 싶을때 
	// Security Config 파일에서 아래와 같이 코드를 적용시켜보자.
	////JSP의 리소스 파일이나 자바스크립트 파일이 저장된 경로는 무시를 한다
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resoures/**", "/static/**", "/css/**", "js/**", "/images/**");
    }

*/    
    
  
    //@Override
  ///  protected void configure(HttpSecurity http) throws Exception {
 /*   	
    	// rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
    	http.httpBasic().disable(); 
        
    	//CSRF 설정을 해제합니다
    	http.csrf().disable(); //초기 개발시에만 설정합니다(보안관련기능으로 개발중에는 매우 귀찮은 존재)
    	
    	// jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
    	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    	// 이 작업은 우선순위가 무척 중요합니다.
    	// 위에서 부터 거르고 거르기 때문에 최 상위에서 / 경로를 허용했다간 
    	// 아래에서 무슨 경로를 제한을 해도 설정이 먹히지 않습니다.
        http.authorizeRequests()
        .antMatchers("/user/**").access("ROLE_USER")
        .antMatchers("/admin/**").access("ROLE_ADMIN")//ROLE_ADMIN 권한이 있는경우만 접근 가능
        .antMatchers("/", "/login", "/login-error").permitAll()//모든 사용자가 접근가능(로그인을 위해 누구나 접속할 수 있는 페이지이기 때문에 누구나 접근이 가능하다)
        .antMatchers("/**").authenticated();// /** 의 경로는 인증을 필요로 한다
*/
    	
		/*
		 * 로그인을 설정
		 * 로그인 페이지는 /, /login (같은 페이지)두 페이지 에서 로그인을 실행할 것이다
		 * 로그인 버튼을 누를시 /login-processing 경로로
		 * 로그인을 실패 할 경우 /login-error
		 * 로그인이 성공할 경우 기본 페이지는 /home
		 * 로그인시 파라미터로 "id", "password"를 받습니다
		 */
    	/*
        http.formLogin()
        .loginPage("/")
        .loginPage("/login")
        .loginProcessingUrl("/login-processing")
        .failureUrl("/login-error")
        .defaultSuccessUrl("/home", true)
        .usernameParameter("id")
        .passwordParameter("password");
        */
		/*
		 * 로그아웃 절차 
		 * /logout 경로로 요청이 들어올 경우 /로 리다이렉트 하고 세션 초기화
		 */
    	/*
        http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//logout 경로로 요청이 들어올 경우 이 경로로 리다이렉트 하고 세션 초기화
        .logoutSuccessUrl("/") // 이 경로로 리다이렉트 하고
        .invalidateHttpSession(true);// 세션 초기화
        
        //로그인 시 authenticate 메소드 호출
        http.authenticationProvider(authProvider);
    	 */
 //   }

}