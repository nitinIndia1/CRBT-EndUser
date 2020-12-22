package com.crbt.api.services.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.crbt.api.services.bean.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "language", schema ="rbtlibyana")
public class Language implements Serializable {

	/**
	 * Created By: Rohit Yadav
	 */
	private static final long serialVersionUID = -3216926844429157442L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@Column(name = "lang_name", nullable = false)
	private String langName;

	@Column(name = "lang_code", nullable = false)
	private String langCode;

	@Column(name = "country_name", nullable = false)
	private String countryName;

	@Column(name = "country_code", nullable = false)
	private String countryCode;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "created_date")
	private Date createdDate;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "last_updated")
	private Date lastUpdated;

	@Column(name = "status")
	private Character status;

	public Language() {
		// TODO Auto-generated constructor stub
	}

	public Language(Integer id, String langName, String langCode, String countryName, String countryCode,
			Date createdDate, Date lastUpdated, Character status) {
		super();
		this.id = id;
		this.langName = langName;
		this.langCode = langCode;
		this.countryName = countryName;
		this.countryCode = countryCode;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LanguageMaster [id=" + id + ", langName=" + langName + ", langCode=" + langCode + ", countryName="
				+ countryName + ", countryCode=" + countryCode + ", createdDate=" + createdDate + ", lastUpdated="
				+ lastUpdated + ", status=" + status + "]";
	}

}
