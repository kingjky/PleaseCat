package com.ssafy.model.service;

import java.io.File;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.model.dao.PostDao;
import com.ssafy.model.dto.PleaseCatException;
import com.ssafy.model.dto.post;

@Service
public class PostServiceImp implements PostService {
	
	@Autowired
	private PostDao dao;
	
	@Value("${custom.path.upload-images}") 
	String dir;

	//게시물 번호로 게시물 1개 검색
	@Override
	public post searchPost(int no) {
		try { 
			post find = dao.searchPost(no);
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

	//회원번호로 회원의 여러게시물 검색
	@Override
	public List<post> searchPostUser(int no) {
		System.out.println(no);
		try { 
			System.out.println("try들어옴");
			List<post> finds = dao.searchPostUser(no);
			System.out.println(finds+"finds결과");
			if(finds == null) {
				throw new PleaseCatException("찾으려는 정보가 없습니다");
		
			} else {
				return finds;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}

	//고양이번호로 고양이의 여러게시물 검색
	@Override
	public List<post> searchPostCat(int no) {
		try { 
			List<post> finds = dao.searchPostCat(no);
			if(finds == null) {
				throw new PleaseCatException("찾으려는 정보가 없습니다");
		
			} else {
				return finds;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}

	//모든 게시물 출력
	@Override
	public List<post> searchAllPost() {
		try { 
			return dao.searchAllPost();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException("게시글 전체 목록을 불러오는데 실패했습니다.");
		}
	}
	
	
	//게시글 등록
	@Override
	public void insertPost(MultipartFile postImg, post Post) {
		try {
			post find = dao.searchPost(Post.getPost_no());
			if(find==null) {
	
			//등록된 image파일 이름을 추출
			String oName = postImg.getOriginalFilename();
			
			//image의 확장자명만 가져옴
			String ext =  oName.substring(oName.lastIndexOf('.')+1);

			//db에 저장될 post의 images에 값을 만들어줌 (파일 불러올 루트)
			Post.setPost_image("post/"+Post.getCat_no()+"."+ext);

			//저장루트 설정 (드라이브 위치부터 하나하나 잡아줘야함)
			//String dir = "C:\\SSAFY\\work_spring\\SpringSafeFood\\src\\main\\resources\\static";

			//저정루트뒤에 불러오는 루트를 붙여줘서 저장함
			File dest = new File(dir+"\\"+Post.getPost_image());
			
			//이미지를 우리가 만든 dest이미지로 transfer
			postImg.transferTo(dest);

			dao.insertPost(Post);

			}else{
				throw new PleaseCatException("게시글이 존재합니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
		
	}

	//게시글 수정
	@Override
	public void updatePost(post Post) {
		try {
			searchPost(Post.getCat_no());
			dao.updatePost(Post);
			System.out.println("게시글 업데이트 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
		
	}

	//게시글 삭제
	@Override
	public void deletePost(int no) {
		try {
			searchPost(no);
			dao.deletePost(no);
			System.out.println(no+"번 게시글 삭제를 완료했습니다.");
		} catch(Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}	
	}

	//좋아요 갯수 수정
	@Override
	public void updateLikes(post Post) {
		try {
			searchPost(Post.getPost_no());
			dao.updateLikes(Post);
			System.out.println("좋아요 갯수 업데이트 성공");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}

	//싫어요 갯수 수정
	@Override
	public void updateUnLikes(post Post) {
		try {
			searchPost(Post.getPost_no());
			dao.updateUnLikes(Post);
			System.out.println("싫어요 갯수 업데이트 성공");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PleaseCatException();
		}
	}
}
