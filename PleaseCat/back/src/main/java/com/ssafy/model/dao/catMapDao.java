package com.ssafy.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.model.dto.cat;
import com.ssafy.model.dto.catMap;

@Repository
@Mapper
public interface catMapDao {
	public List<catMap> searchAllCatLocation();
}
