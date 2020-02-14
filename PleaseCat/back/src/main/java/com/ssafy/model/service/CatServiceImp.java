package com.ssafy.model.service;

import java.io.File;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dao.CatDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.cat;


@Service
public class CatServiceImp implements CatService {
	
	@Autowired
	private CatDao dao;
	
	@Value("${custom.path.upload-images}") 
	String dir;

	//고양이 찾기
	public cat searchCat(int no) {
		try { 
			cat find = dao.searchCat(no);
			if(find == null) {
				throw new PleaseCatException("찾으려는 정보가 없습니다");
		
			} else {
				return find;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}


	//고양이 등록
	public void insertCat(MultipartFile catImg, cat Cat) {
		try {
			cat find = dao.searchCat(Cat.getCat_no());
			if(find != null) {
				throw new PleaseCatException();
			}else {
				//등록된 image파일 이름을 추출
				String oName = catImg.getOriginalFilename();
				
				//image의 확장자명만 가져옴
				String ext =  oName.substring(oName.lastIndexOf('.')+1);

				//db에 저장될 cat의 images에 값을 만들어줌 (파일 불러올 루트)
				Cat.setCat_image("cat/"+dao.findNextCatNo()+"."+ext);

				//저장루트 설정 (드라이브 위치부터 하나하나 잡아줘야함)
				//String dir = "C:\\SSAFY\\work_spring\\SpringSafeFood\\src\\main\\resources\\static";

				//저정루트뒤에 불러오는 루트를 붙여줘서 저장함
				File dest = new File(dir+"\\"+Cat.getCat_image());
				
				//이미지를 우리가 만든 dest이미지로 transfer
				catImg.transferTo(dest);

				dao.insertCat(Cat);
				
				System.out.println("cat 입력 성공");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}

	
	//고양이 정보수정
	public void updateCat(cat Cat) {
		try {
				searchCat(Cat.getCat_no());
				dao.updateCat(Cat);
				System.out.println("고양이 업데이트 성공");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}


	//고양이 정보 삭제
	public void deleteCat(int no) {
		try {
			searchCat(no); //없으면 오류나는 내용이 작성되어있음
			dao.deleteCat(no);
			System.out.println(no+"번 Cat 삭제를 완료했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}

	}


	//고양이 전체목록
	public List<cat> searchAllCat() {
		try { 
			return dao.searchAllCat();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("고양이 전체 목록을 불러오는데 실패했습니다.");
		}
	}
	
	//새로 저장 할 고양이의 cat_no를 구함
	@Override
	public int findNextCatNo() {
		try { 
			return dao.findNextCatNo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("새로 저장 할 고양이의 cat_no를 찾아오는데 실패했습니다.");
		}
	}

}
