package com.crbt.api.services.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "upload_type", schema = "rbtlibyana")
public class UploadType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "status", nullable = false)
	private String status;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_on")
	private Date createdOn;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "updated_on")
	private Date updatedOn;

	public UploadType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UploadType(Integer id, String name, String status, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "UploadType [id=" + id + ", name=" + name + ", status=" + status + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + "]";
	}

}
