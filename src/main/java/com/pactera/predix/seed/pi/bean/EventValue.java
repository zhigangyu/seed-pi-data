package com.pactera.predix.seed.pi.bean;

import java.io.Serializable;

public class EventValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4606803161510587165L;
	private String lable;
	private String value;

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
