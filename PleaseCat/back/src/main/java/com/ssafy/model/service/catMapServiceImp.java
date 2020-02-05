package com.ssafy.model.service;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.model.dao.CatDao;
import com.ssafy.model.dao.catMapDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.cat;
import com.ssafy.model.dto.catMap;


@Service
public class catMapServiceImp implements cat_MapService {
	
	@Autowired
	private catMapDao dao;

	//고양이 전체목록
	public List<catMap> searchAllCatLocation() {
		try {
			return dao.searchAllCatLocation();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("고양이 전체 목록을 불러오는데 실패했습니다.");
		}
	}

}
