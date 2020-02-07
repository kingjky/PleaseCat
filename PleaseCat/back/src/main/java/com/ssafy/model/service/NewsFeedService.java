package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.user;

public interface NewsFeedService {
	public List<user> searchAllNewsFeed(int no);
}
