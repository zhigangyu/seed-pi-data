package com.pactera.predix.seed.pi.bean;

import java.io.Serializable;

public class EventValue implements Serializable {

	private static final long serialVersionUID = -4606803161510587165L;
	private String label;
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	private String value;

	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
