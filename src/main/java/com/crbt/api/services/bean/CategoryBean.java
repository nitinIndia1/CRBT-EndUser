package com.crbt.api.services.bean;

public class CategoryBean {

	private Integer id;
	private String is_active;

	public CategoryBean() {
		// TODO Auto-generated constructor stub
	}

	public CategoryBean(Integer id, String is_active) {
		super();
		this.id = id;
		this.is_active = is_active;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	@Override
	public String toString() {
		return "CategoryBean [id=" + id + ", is_active=" + is_active + "]";
	}

}
