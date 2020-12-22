package com.crbt.api.services.bean;

public class SubscriptionRequestBean {

	private Integer subscriber_id;
	private String status;

	public SubscriptionRequestBean() {
		// TODO Auto-generated constructor stub
	}

	public SubscriptionRequestBean(Integer subscriber_id, String status) {
		super();
		this.subscriber_id = subscriber_id;
		this.status = status;
	}

	public Integer getSubscriber_id() {
		return subscriber_id;
	}

	public void setSubscriber_id(Integer subscriber_id) {
		this.subscriber_id = subscriber_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SubscriptionRequestBean [subscriber_id=" + subscriber_id + ", status=" + status + "]";
	}

}
