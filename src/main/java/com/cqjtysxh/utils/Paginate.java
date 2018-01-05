package com.cqjtysxh.utils;

import com.jfinal.core.Controller;

public class Paginate {
	private Integer pageNumber;
	private Integer pageSize;

	private Paginate(Controller c) {
		pageNumber = c.getParaToInt("pageNumber") == null ? 1 : c
				.getParaToInt("pageNumber");
		pageSize = c.getParaToInt("numPerPage") == null ? 10 : c
				.getParaToInt("numPerPage");
		c.setAttr("numPerPage", pageSize);
	}

	/**
	 * 工厂方法
	 * 
	 * @param c
	 * @return
	 */
	public static Paginate init(Controller c) {
		return new Paginate(c);
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

}
