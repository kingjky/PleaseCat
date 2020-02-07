package com.ssafy.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dto.post;

public interface PostService {
	public post searchPost(int no);
	public List<post> searchPostUser(int no);
	public List<post> searchPostCat(int no);
	public List<post> searchAllPost();
	public void insertPost(MultipartFile postImg, post user);
	public void updatePost(post user);
	public void deletePost(int no);
	public void updateLikes(post post);
	public void updateUnLikes(post post);
}
