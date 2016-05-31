package com.pactera.predix.seed.pi.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Item<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4781015749804409940L;
	private List<T> items;
	private int total;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
