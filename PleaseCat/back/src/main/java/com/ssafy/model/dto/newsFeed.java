package com.ssafy.model.dto;

public class newsFeed {
	
	private String cat_name;
	private String cat_image;
	private String user_id;
	private int post_like;
	private int post_unlike;
	private String post_image;
	private String post_content;
	private String post_time;
	private int post_no;
	private boolean like;
	private boolean unlike;
	
	public newsFeed() {
		super();
	}

	public newsFeed(String cat_name, String cat_image, String user_id, int post_like, int post_unlike,
			String post_image, String post_content, String post_time, int post_no) {
		super();
		this.cat_name = cat_name;
		this.cat_image = cat_image;
		this.user_id = user_id;
		this.post_like = post_like;
		this.post_unlike = post_unlike;
		this.post_image = post_image;
		this.post_content = post_content;
		this.post_time = post_time;
		this.post_no = post_no;
		this.like =false;
		this.unlike=false;
	}
	
	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public String getCat_image() {
		return cat_image;
	}

	public void setCat_image(String cat_image) {
		this.cat_image = cat_image;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getPost_like() {
		return post_like;
	}

	public void setPost_like(int post_like) {
		this.post_like = post_like;
	}

	public int getPost_unlike() {
		return post_unlike;
	}

	public void setPost_unlike(int post_unlike) {
		this.post_unlike = post_unlike;
	}

	public String getPost_image() {
		return post_image;
	}

	public void setPost_image(String post_image) {
		this.post_image = post_image;
	}

	public String getPost_content() {
		return post_content;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}

	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}

	public int getPost_no() {
		return post_no;
	}

	public void setPost_no(int post_no) {
		this.post_no = post_no;
	}
}
