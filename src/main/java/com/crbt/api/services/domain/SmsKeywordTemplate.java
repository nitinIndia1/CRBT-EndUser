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
@Table(name = "sms_keyword_templates", schema = "rbtlibyana")
public class SmsKeywordTemplate implements Serializable {

	private static final long serialVersionUID = -9085573759481362089L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;
	
	@Column( name = "sms_keyword_en", nullable = false )
	private String smsKeywordEn;
	
	@Column( name = "sms_keyword_ar", nullable = false )
	private String smsKeywordAr;
	
	@Column( name = "success_reply_en", nullable = false )
	private String successReplyEn;
	
	@Column( name = "success_reply_ar", nullable = false )
	private String successReplyAr;
	
	@Column( name = "failed_reply_en", nullable = false )
	private String failedReplyEn;
	
	@Column( name = "failed_reply_ar", nullable = false )
	private String failedReplyAr;
	
	@Column( name = "interim_reply_en", nullable = false )
	private String interimReplyEn;
	
	@Column( name = "interim_reply_ar", nullable = false )
	private String interimReplyAr;
	
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "create_date")
	private Date createDate;
	
	@Column( name = "status", nullable = false )
	private Integer status;
	
	@Column( name = "keyword_type", nullable = false )
	private String keywordType;
	
	public SmsKeywordTemplate() {}

	public SmsKeywordTemplate(Integer id, String smsKeywordEn, String smsKeywordAr, String successReplyEn,
			String successReplyAr, String failedReplyEn, String failedReplyAr, String interimReplyEn,
			String interimReplyAr, Date createDate, Integer status, String keywordType) {
		super();
		this.id = id;
		this.smsKeywordEn = smsKeywordEn;
		this.smsKeywordAr = smsKeywordAr;
		this.successReplyEn = successReplyEn;
		this.successReplyAr = successReplyAr;
		this.failedReplyEn = failedReplyEn;
		this.failedReplyAr = failedReplyAr;
		this.interimReplyEn = interimReplyEn;
		this.interimReplyAr = interimReplyAr;
		this.createDate = createDate;
		this.status = status;
		this.keywordType = keywordType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSmsKeywordEn() {
		return smsKeywordEn;
	}

	public void setSmsKeywordEn(String smsKeywordEn) {
		this.smsKeywordEn = smsKeywordEn;
	}

	public String getSmsKeywordAr() {
		return smsKeywordAr;
	}

	public void setSmsKeywordAr(String smsKeywordAr) {
		this.smsKeywordAr = smsKeywordAr;
	}

	public String getSuccessReplyEn() {
		return successReplyEn;
	}

	public void setSuccessReplyEn(String successReplyEn) {
		this.successReplyEn = successReplyEn;
	}

	public String getSuccessReplyAr() {
		return successReplyAr;
	}

	public void setSuccessReplyAr(String successReplyAr) {
		this.successReplyAr = successReplyAr;
	}

	public String getFailedReplyEn() {
		return failedReplyEn;
	}

	public void setFailedReplyEn(String failedReplyEn) {
		this.failedReplyEn = failedReplyEn;
	}

	public String getFailedReplyAr() {
		return failedReplyAr;
	}

	public void setFailedReplyAr(String failedReplyAr) {
		this.failedReplyAr = failedReplyAr;
	}

	public String getInterimReplyEn() {
		return interimReplyEn;
	}

	public void setInterimReplyEn(String interimReplyEn) {
		this.interimReplyEn = interimReplyEn;
	}

	public String getInterimReplyAr() {
		return interimReplyAr;
	}

	public void setInterimReplyAr(String interimReplyAr) {
		this.interimReplyAr = interimReplyAr;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}

	@Override
	public String toString() {
		return "SmsKeywordTemplate [id=" + id + ", smsKeywordEn=" + smsKeywordEn + ", smsKeywordAr=" + smsKeywordAr
				+ ", successReplyEn=" + successReplyEn + ", successReplyAr=" + successReplyAr + ", failedReplyEn="
				+ failedReplyEn + ", failedReplyAr=" + failedReplyAr + ", interimReplyEn=" + interimReplyEn
				+ ", interimReplyAr=" + interimReplyAr + ", createDate=" + createDate + ", status=" + status
				+ ", keywordType=" + keywordType + "]";
	}
	
	
}
