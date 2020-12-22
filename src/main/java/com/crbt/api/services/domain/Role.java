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
@Table(name = "role", schema = "rbtlibyana")
public class Role implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = 1259632250708036201L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "role_name", nullable = false)
	private String roleName;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_on")
	private Date createdOn;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "updated_on")
	private Date updatedOn;

	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(Integer id, String roleName, Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		return "Role [id=" + id + ", roleName=" + roleName + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ "]";
	}

}
