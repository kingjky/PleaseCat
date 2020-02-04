package com.ssafy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;   
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//Jwt가 유효한 토큰인지 인증하기 위한 Filter입니다. 
//config.security 하위에 Class를 생성합니다. 
//이 필터는 Security 설정 시 
//UsernamePasswordAuthenticationFilter앞에 세팅할 것입니다.

/*
토큰을 생성하고 검증하는 컴포넌트를 완성했지만 실제로 이 컴포넌트를 이용하는 것은 
인증 작업을 진행하는 Filter 입니다. 
이 필터는 검증이 끝난 JWT로부터 유저정보를 받아와서 
UsernamePasswordAuthenticationFilter 로 전달해야 할 것입니다. 
*/

public class JwtAuthenticationFilter extends GenericFilterBean {
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
    // Jwt Provier 주입
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    	//헤더에서 JWT를 받아옵니다.
    	String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

    	//유요한 토큰인지 확인합니다.
    	if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
    		Authentication auth = jwtTokenProvider.getAuthentication(token);
    		// SecurityContext에 Authentication 객체를 저장합니다.
    		SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
