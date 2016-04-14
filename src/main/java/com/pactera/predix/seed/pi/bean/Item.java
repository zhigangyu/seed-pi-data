package com.pactera.predix.seed.pi.bean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4781015749804409940L;
	private List<ItemField> items;
	private int total;

	public List<ItemField> getItems() {
		return items;
	}

	public void setItems(List<ItemField> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
