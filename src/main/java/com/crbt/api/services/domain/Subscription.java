package com.crbt.api.services.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "subscription", schema = "rbtlibyana")
public class Subscription implements Serializable {

	/**
	 * Developed BY : Rohit Yadav
	 */
	private static final long serialVersionUID = -4227146525321289735L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "subscriber_id", referencedColumnName = "id")
	private Subscriber subscriber;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "content_songs_id", referencedColumnName = "song_id")
	private ContentSongs contentSongs;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;

	// @Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "subscription_type", nullable = false)
	private String subscriptionType;

	@Column(name = "status")
	private String status;

	@Column(name = "retry_count")
	private Integer retryCount;

	@Column(name = "charging_status")
	private String chargingStatus;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "last_retry_on")
	private Date lastRetryOn;

	@Column(name = "remarks")
	private String remarks;

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", timezone = "IST")
	@Column(name = "last_updated_on")
	private Date lastUpdatedOn;
	
	public String getSubscriptionFrom() {
		return subscriptionFrom;
	}

	public void setSubscriptionFrom(String subscriptionFrom) {
		this.subscriptionFrom = subscriptionFrom;
	}

	@Column(name = "subscription_from")
	private String subscriptionFrom;

	public Subscription() {
		// TODO Auto-generated constructor stub
	}

	public Subscription(Integer id, Subscriber subscriber, ContentSongs contentSongs, Date startDate, Date endDate,
			String subscriptionType, String status, Integer retryCount, String chargingStatus, Date lastRetryOn,
			String remarks, Date lastUpdatedOn) {
		super();
		this.id = id;
		this.subscriber = subscriber;
		this.contentSongs = contentSongs;
		this.startDate = startDate;
		this.endDate = endDate;
		this.subscriptionType = subscriptionType;
		this.status = status;
		this.retryCount = retryCount;
		this.chargingStatus = chargingStatus;
		this.lastRetryOn = lastRetryOn;
		this.remarks = remarks;
		this.lastUpdatedOn = lastUpdatedOn;
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

	public ContentSongs getContentSongs() {
		return contentSongs;
	}

	public void setContentSongs(ContentSongs contentSongs) {
		this.contentSongs = contentSongs;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public String getChargingStatus() {
		return chargingStatus;
	}

	public void setChargingStatus(String chargingStatus) {
		this.chargingStatus = chargingStatus;
	}

	public Date getLastRetryOn() {
		return lastRetryOn;
	}

	public void setLastRetryOn(Date lastRetryOn) {
		this.lastRetryOn = lastRetryOn;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", subscriber=" + subscriber + ", contentSongs=" + contentSongs
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", subscriptionType=" + subscriptionType
				+ ", status=" + status + ", retryCount=" + retryCount + ", chargingStatus=" + chargingStatus
				+ ", lastRetryOn=" + lastRetryOn + ", remarks=" + remarks + ", lastUpdatedOn=" + lastUpdatedOn + "]";
	}

}
