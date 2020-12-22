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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "subscriber", schema = "rbtlibyana", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "msisdn" }) })
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Subscriber implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6953462389162725790L;

	/**
	 * Developed BY : Rohit Yadav
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@JsonView(Views.Public.class)
	@Column(name = "msisdn", nullable = false)
	private String msisdn;

	@JsonView(Views.Public.class)
	@Column(name = "name")
	private String name;

	@Column(name = "password", nullable = false)
	private String password;

	@JsonView(Views.Internal.class)
	@Column(name = "email")
	private String email;

	@JsonView(Views.Public.class)
	@Column(name = "location")
	private String location;

	@JsonView(Views.Public.class)
	@Column(name = "profile_pic_path")
	private String profilePicPath;

	@Column(name = "is_app_user", nullable = false)
	private String isAppUser;

	@Column(name = "device_type")
	private String deviceType;

	@Column(name = " is_active", nullable = false)
	private String isActive;

	@JsonView(Views.PageData.class)
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_on")
	private Date createdOn;

	@JsonView(Views.PageData.class)
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "updated_on")
	private Date updatedOn;

	@ManyToOne
	@JoinColumn(name = "lang_id", referencedColumnName = "id")
	private Language changeLanguage;

	public Subscriber() {
		// TODO Auto-generated constructor stub
	}

	public Subscriber(Integer id, String msisdn, String name, String password, String email, String location,
			String profilePicPath, String isAppUser, String deviceType, String isActive, Date createdOn, Date updatedOn,
			Language changeLanguage) {
		super();
		this.id = id;
		this.msisdn = msisdn;
		this.name = name;
		this.password = password;
		this.email = email;
		this.location = location;
		this.profilePicPath = profilePicPath;
		this.isAppUser = isAppUser;
		this.deviceType = deviceType;
		this.isActive = isActive;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.changeLanguage = changeLanguage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public String getIsAppUser() {
		return isAppUser;
	}

	public void setIsAppUser(String isAppUser) {
		this.isAppUser = isAppUser;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public Language getChangeLanguage() {
		return changeLanguage;
	}

	public void setChangeLanguage(Language changeLanguage) {
		this.changeLanguage = changeLanguage;
	}

	@Override
	public String toString() {
		return "Subscriber [id=" + id + ", msisdn=" + msisdn + ", name=" + name + ", password=" + password + ", email="
				+ email + ", location=" + location + ", profilePicPath=" + profilePicPath + ", isAppUser=" + isAppUser
				+ ", deviceType=" + deviceType + ", isActive=" + isActive + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", changeLanguage=" + changeLanguage + "]";
	}

	
	
}
