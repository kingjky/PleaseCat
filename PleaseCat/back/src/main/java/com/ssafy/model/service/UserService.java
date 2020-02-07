package com.ssafy.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dto.user;

public interface UserService  {
	public user searchUser(int no) ;
	public void insertUser(MultipartFile userImg, user User) ;
	public void updateUser(user User);
	public void deleteUser(int no);
	public List<user> searchAllUser();
	public user searchUserEmail(String user_email);
	public String login(String user_email, String user_pw);
	public String checkToken(String token);
}
