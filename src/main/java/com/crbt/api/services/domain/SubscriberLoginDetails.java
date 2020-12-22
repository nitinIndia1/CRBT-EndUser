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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "subscriber_login_details", schema = "rbtlibyana")
public class SubscriberLoginDetails implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = -3036744907347700335L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "subscriber_id", referencedColumnName = "id")
	private Subscriber subscriber;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "login_date", nullable = false)
	private Date loginDate;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "logout_date")
	private Date logoutDate;

	@Column(name = "login_method", nullable = false)
	private String loginMethod;

	@Column(name = "generated_otp", nullable = false)
	private String generatedOtp;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "otp_expiry_date", nullable = false)
	private Date otpExpireDate;

	public SubscriberLoginDetails() {
		// TODO Auto-generated constructor stub
	}

	public SubscriberLoginDetails(Integer id, Subscriber subscriber, Date loginDate, Date logoutDate,
			String loginMethod, String generatedOtp, Date otpExpireDate) {
		super();
		this.id = id;
		this.subscriber = subscriber;
		this.loginDate = loginDate;
		this.logoutDate = logoutDate;
		this.loginMethod = loginMethod;
		this.generatedOtp = generatedOtp;
		this.otpExpireDate = otpExpireDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public String getGeneratedOtp() {
		return generatedOtp;
	}

	public void setGeneratedOtp(String generatedOtp) {
		this.generatedOtp = generatedOtp;
	}

	public Date getOtpExpireDate() {
		return otpExpireDate;
	}

	public void setOtpExpireDate(Date otpExpireDate) {
		this.otpExpireDate = otpExpireDate;
	}

	@Override
	public String toString() {
		return "SubscriberLoginDetails [id=" + id + ", subscriber=" + subscriber + ", loginDate=" + loginDate
				+ ", logoutDate=" + logoutDate + ", loginMethod=" + loginMethod + ", generatedOtp=" + generatedOtp
				+ ", otpExpireDate=" + otpExpireDate + "]";
	}

}
