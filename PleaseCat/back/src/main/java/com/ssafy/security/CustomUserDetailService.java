package com.ssafy.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ssafy.model.dao.UserDao;
import com.ssafy.model.dto.PleaseCatException;

import lombok.RequiredArgsConstructor;

//토큰에 세팅된 유저 정보로 회원정보를 조회하는 UserDetailsService를 재정의 합니다.

// 토큰에 저장된 유저 정보를 활용해야 하기 때문에 
// CustomUserDetatilService 라는 이름의 클래스를 만들고 
// UserDetailsService를 상속받아 재정의 하는 과정을 진행합니다.

@RequiredArgsConstructor
@Service
public class CustomUserDetailService{// implements UserDetailsService {

    private UserDao dao;
   
    public UserDetails loadUserByUsername(String email) {
    	try {
    		return dao.searchUserEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("회원을 찾을수 없습니다.");
		}
    }
}
