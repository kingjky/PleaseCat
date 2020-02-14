package com.ssafy.model.service;

import java.util.HashMap; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dao.Following_catDao;
import com.ssafy.model.dao.NewsFeedDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.following_cat;
import com.ssafy.model.dto.user;


@Service
public class NewsFeedServiceImp implements NewsFeedService {

	@Autowired
	private NewsFeedDao dao;
	//뉴스피드 정보 출력
		@Override
		public List<user> searchAllNewsFeed(int no) {
			try { 
				return dao.searchAllNewsFeed(no);
			} catch (Exception e) {
				e.printStackTrace();
				throw new PleaseCatException("뉴스피드 목록을 불러오는데 실패했습니다.");
			}
		}
}
