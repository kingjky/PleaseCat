package com.ssafy.model.dto;

public class ranking {
	private int cat_no;
	private int user_no;
	private int rank_point; 
	private String user_id;
	
	
	
	
	public ranking() {
		super();
	}
	public ranking(int cat_no, int user_no, int rank_point) {
		super();
		this.cat_no = cat_no;
		this.user_no = user_no;
		this.rank_point = rank_point;
	}
	public int getCat_no() {
		return cat_no;
	}
	public void setCat_no(int cat_no) {
		this.cat_no = cat_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getRank_point() {
		return rank_point;
	}
	public void setRank_point(int rank_point) {
		this.rank_point = rank_point;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "ranking [cat_no=" + cat_no + ", user_no=" + user_no + ", rank_point=" + rank_point + "]";
	}
	
	
}
