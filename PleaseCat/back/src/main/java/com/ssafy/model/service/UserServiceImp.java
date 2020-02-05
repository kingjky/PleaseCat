package com.ssafy.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dao.UserDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.user;
import com.ssafy.util.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import work.crypt.BCrypt;
import work.crypt.SHA256;


@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDao dao;
	@Autowired
	private JwtTokenProvider jwt;

	SHA256 sha = SHA256.getInsatnce();


	//회원번호로 회원검색
	public user searchUser(int no) {
		try { 
			user User = dao.searchUser(no);
			if(User == null) {
				throw new PleaseCatException("찾으려는 정보가 없습니다");
		
			} else {
				return User;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}
	
	//회원이메일로 회원검색
	public user searchUserEmail(String user_email) {
		try { 
			user User = dao.searchUserEmail(user_email);
			if(User == null) {
				throw new PleaseCatException("찾으려는 정보가 없습니다");
			} else {
				return User;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}

	
	//회원가입을 통한 회원추가
	public void insertUser(user User) {
		try {
			String orgPass = User.getUser_pw();
            String shaPass = sha.getSha256(orgPass.getBytes());
        	String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	
			user find = dao.searchUserEmail(User.getUser_email());
			if(find != null) {
				throw new PleaseCatException();
			}else {
				User.setUser_pw(bcPass);
				dao.insertUser(User);
				System.out.println("user 입력 성공");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}


	//회원정보 수정
	@Override
	public void updateUser(user User) {
		try {
			searchUser(User.getUser_no());
			String orgPass = User.getUser_pw();
            String shaPass = sha.getSha256(orgPass.getBytes());
        	String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	User.setUser_pw(bcPass);
			dao.updateUser(User);
			System.out.println("user 업데이트 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
		
	}


	//회원정보 삭제
	@Override
	public void deleteUser(int no) {
		try {
			searchUser(no);
			dao.deleteUser(no);
			System.out.println(no+"번 user 삭제를 완료했습니다.");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}


	//전체회원 목록 출력
	@Override
	public List<user> searchAllUser() {
		try { 
			return dao.searchAllUser();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("회원 전체 목록을 불러오는데 실패했습니다.");
		}
	}
	
	//회원 로그인
	public String login(String user_email, String user_pw){
		try {
			user User = searchUserEmail(user_email);
			String orgPass = user_pw;
            String shaPass = sha.getSha256(orgPass.getBytes());
            
				if(BCrypt.checkpw(shaPass,User.getUser_pw())) {
					return jwt.createToken(User);
				}else {
					throw new PleaseCatException("비밀 번호 오류");
				}
		} catch (Exception e) {
			throw new PleaseCatException();
		}
	}
	
	  public String checkToken(String token) {
	    	try {

	    		return jwt.getUserPk(token); //수행 되면 정상
	    	} catch (ExpiredJwtException exception) {
	    		//토큰 만료
	    		throw new PleaseCatException("토큰만료");
	    		//return false;
	    	} catch (JwtException exception) {
	    		//토큰 변조
	    		throw new PleaseCatException("토큰변조");
	    		//return false;
	    	} 
	    }
}
