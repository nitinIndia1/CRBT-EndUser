package com.crbt.api.services.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "category", schema = "rbtlibyana", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "category_name" })})
@DynamicUpdate
public class Category implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = 3009140211269927535L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@JsonView( Views.ExtendedPublic.class )
	@Column(name = "category_name", nullable = false, updatable= false)
	private String categoryName;

	@Column(name = "is_active", nullable = false)
	private String isActive;

	@Column(name = "priority", nullable = false)
	private Integer priority;

	@JsonView( Views.WithMapping.class )
	@Column(name = "category_name_ar", nullable = false)
	private String categoryNameAr;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Integer id, String categoryName, String isActive, Integer priority, String categoryNameAr) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.isActive = isActive;
		this.priority = priority;
		this.categoryNameAr = categoryNameAr;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCategoryNameAr() {
		return categoryNameAr;
	}

	public void setCategoryNameAr(String categoryNameAr) {
		this.categoryNameAr = categoryNameAr;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", isActive=" + isActive + ", priority="
				+ priority + ", categoryNameAr=" + categoryNameAr + "]";
	}

}
