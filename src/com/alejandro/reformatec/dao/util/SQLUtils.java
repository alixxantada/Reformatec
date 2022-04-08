package com.alejandro.reformatec.dao.util;


import java.util.List;

public class SQLUtils {
	// le pasamos al metodo una lista de ids (la llamamos ids) 
	public static final String toIN(List<Long> ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<ids.size()-1;i++) {
			sb.append(ids.get(i)).append(",");
		}
		sb.append(ids.get(ids.size()-1));
		return sb.toString(); 
	}

	public static final String toIN2(List<Integer> ids) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i<ids.size()-1;i++) {
			sb.append(ids.get(i)).append(",");
		}
		sb.append(ids.get(ids.size()-1));
		return sb.toString(); 
	}

	public static final String toINBuilder(List<Integer> ids) {
		StringBuilder a = new StringBuilder();
		for(int i=0; i<ids.size()-1;i++) {
			a.append("?,");
		}
		a.append("?");
		return a.toString();
	}
}
