package com.crbt.api.services.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "user", schema = "rbtlibyana")
public class User implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = -7472185467445882628L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView( Views.Public.class)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String userName;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "mobile_no", nullable = false)
	private String mobileNo;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_on")
	private Date createdOn;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "update_on")
	private Date updateOn;

	@Column(name = "is_active", nullable = false)
	private String isActive;

	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Column(name = "location", nullable = false)
	private String location;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer id, String userName, String password, String email, String mobileNo, Date createdOn,
			Date updateOn, String isActive, String companyName, String location, Role role) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.mobileNo = mobileNo;
		this.createdOn = createdOn;
		this.updateOn = updateOn;
		this.isActive = isActive;
		this.companyName = companyName;
		this.location = location;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", mobileNo=" + mobileNo + ", createdOn=" + createdOn + ", updateOn=" + updateOn + ", isActive="
				+ isActive + ", companyName=" + companyName + ", location=" + location + ", role=" + role + "]";
	}

	

}
