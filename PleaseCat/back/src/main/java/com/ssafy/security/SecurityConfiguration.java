package com.ssafy.security;

import lombok.RequiredArgsConstructor;    
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
            .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션필요없으므로 생성안함.
            .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                    .antMatchers("/*/user/insert", "/*/user/login").permitAll() // 가입 및 인증 주소는 누구나 접근가능
                    .antMatchers(HttpMethod.GET, "/exception/**").permitAll() // 등록된 GET요청 리소스는 누구나 접근가능
                    .anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
            .and()
            	//인증(로그인)은 되었으나 해당 요청에 대한 권한이 없을 경우에는 AccessDeniedHandler 부분에서 AccessDeniedException 이 발생
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
            //인증이 되지 않았을 경우(비로그인)에는 AuthenticationEntryPoint 부분에서 AuthenticationException 을 발생
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
            // jwt token 필터를 id/password 인증 필터 전에 넣어라.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

 // 만약 로그인 필요없이 특정 path에 접근하고 싶을때 
    @Override // ignore swagger security config
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }
}
