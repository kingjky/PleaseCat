package com.ssafy.model.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.model.dto.cat;
import com.ssafy.model.dto.catMap;

public interface cat_MapService {
	public List<catMap> searchAllCatLocation();
}
