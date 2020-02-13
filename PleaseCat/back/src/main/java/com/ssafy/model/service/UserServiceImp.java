package com.ssafy.model.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dao.UserDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.post;
import com.ssafy.model.dto.user;
import com.ssafy.util.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import work.crypt.BCrypt;
import work.crypt.SHA256;

@Component
@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserDao dao;
	@Autowired
	private JwtTokenProvider jwt;
	
	@Value("${custom.path.upload-images}") 
	String dir;

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
	public void insertUser(MultipartFile userImg,user User) {
		try {
			String orgPass = User.getUser_pw();
            String shaPass = sha.getSha256(orgPass.getBytes());
        	String bcPass = BCrypt.hashpw(shaPass, BCrypt.gensalt());
        	
			user find = dao.searchUserEmail(User.getUser_email());
			if(find != null) {
				throw new PleaseCatException();
			}else {
				User.setUser_pw(bcPass);
		
				//등록된 image파일 이름을 추출
				if(userImg != null) {
					String oName = userImg.getOriginalFilename();
					
					//image의 확장자명만 가져옴
					String ext =  oName.substring(oName.lastIndexOf('.')+1);
	
					//db에 저장될 post의 images에 값을 만들어줌 (파일 불러올 루트)
					User.setUser_image("user/"+User.getUser_no()+"."+ext);
	
					//저장루트 설정 (드라이브 위치부터 하나하나 잡아줘야함)
					//String dir = "C:\\SSAFY\\work_spring\\SpringSafeFood\\src\\main\\resources\\static";
					
					
					
					//저정루트뒤에 불러오는 루트를 붙여줘서 저장함
					File dest = new File(dir+"\\"+User.getUser_image());
					
					//이미지를 우리가 만든 dest이미지로 transfer
					userImg.transferTo(dest);
				}
				
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
	public String login(user tmp){
		try {
			
			user User = searchUserEmail(tmp.getUser_email());
			String orgPass = tmp.getUser_pw();
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
	    		String str = jwt.getUserPk(token); //수행 되면 정상
	    		System.out.println(str);
	    		return str; //수행 되면 정상
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
