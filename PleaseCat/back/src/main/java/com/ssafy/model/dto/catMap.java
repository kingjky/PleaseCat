package com.ssafy.model.dto;

public class catMap {
	
	private String cat_name;
	private String cat_image;
	private int cat_no;
	private String cat_positionX;
	private String cat_positionY;
	
	
	public catMap() {
		super();
	}

	public catMap(String cat_name, String cat_image, int cat_no, String cat_positionX, String cat_positionY) {
		super();
		this.cat_name = cat_name;
		this.cat_image = cat_image;
		this.cat_no = cat_no;
		this.cat_positionX = "1234";// cat_positionX;
		this.cat_positionY = "1234";// cat_positionY;
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

	public int getCat_no() {
		return cat_no;
	}

	public void setCat_no(int cat_no) {
		this.cat_no = cat_no;
	}

	public String getCat_positionX() {
		return cat_positionX;
	}

	public void setCat_positionX(String cat_positionX) {
		this.cat_positionX = cat_positionX;
	}

	public String getCat_positionY() {
		return cat_positionY;
	}

	public void setCat_positionY(String cat_positionY) {
		this.cat_positionY = cat_positionY;
	}

	
}
