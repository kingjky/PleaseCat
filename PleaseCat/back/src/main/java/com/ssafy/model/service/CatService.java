package com.ssafy.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dto.cat;

public interface CatService {
	public cat searchCat(int no);
	public void insertCat(MultipartFile catImg,cat Cat);
	public void updateCat(cat Cat);
	public void deleteCat(int no);
	public List<cat> searchAllCat();
	public int findNextCatNo();
}
