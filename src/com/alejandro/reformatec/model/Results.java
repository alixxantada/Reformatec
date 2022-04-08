package com.alejandro.reformatec.model;

import java.util.ArrayList;
import java.util.List;

import com.alejandro.reformatec.dao.util.AbstractValueObject;


//TODO LA <T> era el tipo de dato ?
public class Results<T> extends AbstractValueObject {

	private List<T> data = null;
	private int total = 0;

	public Results() {
		this.data = new ArrayList<T>();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}

