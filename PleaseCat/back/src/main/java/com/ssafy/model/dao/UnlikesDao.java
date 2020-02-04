package com.ssafy.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.likes;
import com.ssafy.model.dto.unlikes;

@Repository
@Mapper
public interface UnlikesDao {
	public unlikes searchUnlikes(HashMap<String, Object> map);
	public void insertUnlikes(HashMap<String, Object> map);
	public void deleteUnlikes(HashMap<String, Object> map);
	public List<likes> searchAllUnLikes(int user_no);
}
