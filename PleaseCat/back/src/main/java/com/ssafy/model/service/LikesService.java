package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.likes;

public interface LikesService {
	public String searchLikes(int user_no, int post_no);
	public void insertLikes(int user_no, int post_no);
	public void deleteLikes(int user_no, int post_no);
	public List<likes> searchAllLikes(int user_no);
	List<likes> searchAllLikesOfUser(int user_no);
	List<likes> searchAllLikesOfPost(int post_no);
}
