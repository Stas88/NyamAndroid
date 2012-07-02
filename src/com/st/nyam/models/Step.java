package com.st.nyam.models;

import java.io.Serializable;


public class Step implements Serializable {

	private int number;
	private String img_url;
	private String instruction;

	public Step() {}

	public Step(int number, String img_url, String instruction) {
		super();
		this.number = number;
		this.img_url = img_url;
		this.instruction = instruction;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

}