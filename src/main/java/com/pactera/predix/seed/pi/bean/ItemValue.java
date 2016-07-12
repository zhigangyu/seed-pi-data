package com.pactera.predix.seed.pi.bean;

import java.io.Serializable;
import java.util.Date;

public class ItemValue implements Serializable {

	private static final long serialVersionUID = -5201252113837932663L;
	private Date time;
	private String value;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
