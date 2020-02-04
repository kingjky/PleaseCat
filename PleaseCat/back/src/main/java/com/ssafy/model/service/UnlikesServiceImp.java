package com.ssafy.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.model.dao.UnlikesDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.likes;
import com.ssafy.model.dto.unlikes;

@Service
public class UnlikesServiceImp implements UnlikesService {

	@Autowired
	private UnlikesDao dao;
	
	HashMap<String, Object> map;
	
	//싫어요 목록 출력
	@Override
	public List<likes> searchAllUnLikes(int user_no) {
		try { 
			return dao.searchAllUnLikes(user_no);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("좋아요 전체 목록을 불러오는데 실패했습니다.");
		}
	}
	
	//싫어요 여부
	@Override
	public String searchUnlikes(int user_no, int post_no) {
		try {
			map = new HashMap<String, Object>();
			map.put("user_no", user_no);
			map.put("post_no", post_no);
			unlikes find = dao.searchUnlikes(map);
			if(find==null) {
				return "execute";
			}else {
				return "cancel";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("싫어요 탐색중 오류");
		}
	}

	//싫어요 
	@Override
	public void insertUnlikes(int user_no, int post_no) {
		try {
			String answer = searchUnlikes(user_no, post_no);
			if(answer=="execute") {
				map = new HashMap<String, Object>();
				map.put("user_no", user_no);
				map.put("post_no", post_no);
				dao.insertUnlikes(map);
				System.out.println("싫어요 누를게요~");
			}else {
				throw new PleaseCatException("이미 싫어요 눌렀습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("싫어요 실행중 오류");
		}

	}

	//싫어요 취소
	@Override
	public void deleteUnlikes(int user_no, int post_no) {
		try {
		String answer = searchUnlikes(user_no, post_no);
		if(answer=="cancel") {
			map = new HashMap<String, Object>();
			map.put("user_no", user_no);
			map.put("post_no", post_no);
			dao.deleteUnlikes(map);
			System.out.println("싫어요 취소합니다.");
		}else {
			throw new PleaseCatException("없는데 우째 삭제해요");
			}
		} catch (Exception e) {
		e.printStackTrace();
		throw new PleaseCatException("싫어요 실행중 오류");
		}
	}
}
