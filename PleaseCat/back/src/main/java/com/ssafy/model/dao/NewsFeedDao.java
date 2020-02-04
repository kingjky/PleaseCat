package com.ssafy.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.cat;
import com.ssafy.model.dto.user;

@Repository
@Mapper
public interface NewsFeedDao {
	public List<user> searchAllNewsFeed(int follower_no);
}
