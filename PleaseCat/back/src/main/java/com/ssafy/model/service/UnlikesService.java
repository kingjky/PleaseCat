package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.likes;

public interface UnlikesService {
	public String searchUnlikes(int user_no, int post_no);
	public void insertUnlikes(int user_no, int post_no);
	public void deleteUnlikes(int user_no, int post_no);
	List<likes> searchAllUnLikes(int user_no);
}
