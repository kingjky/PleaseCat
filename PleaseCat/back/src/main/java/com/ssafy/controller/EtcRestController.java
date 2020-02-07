package com.ssafy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.model.service.Etc_Service;
import com.ssafy.model.service.Following_catService;
import com.ssafy.model.service.cat_MapService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/api/Etc")
@RestController
public class EtcRestController {
	
	@Autowired
	private Etc_Service etcService;
	
	@Autowired
	private cat_MapService catMapService;
	
	@ExceptionHandler
	public ResponseEntity<Map<String, Object>> handle(Exception e){
		return handleFail(e.getMessage(), HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> handleSuccess(Object data){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "ok");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String, Object>> handleFail(Object data, HttpStatus state){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("state", "fail");
		resultMap.put("data", data);
		return new ResponseEntity<Map<String,Object>>(resultMap, state); 
	}
	
	@ApiOperation("모든 고양이 지도 정보를 찾는다.")
	@GetMapping("/searchAllCatLocation/")
	public ResponseEntity<Map<String, Object>> searchAllCatLocation() throws Exception{
		return handleSuccess(catMapService.searchAllCatLocation());
	}
	
	@ApiOperation("모든 뉴스피드 정보를 찾는다.")
	@GetMapping("/searchAll/{follower_no}")
	public ResponseEntity<Map<String, Object>> searchAllNewsFeed(@RequestParam int follower_no) throws Exception{
		return handleSuccess(etcService.searchAllNewsFeed(follower_no));
	}
}
