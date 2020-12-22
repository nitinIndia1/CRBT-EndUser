package com.crbt.api.services.bean;

import java.util.List;

import com.crbt.api.services.domain.Subscription;

public class SubscriptionResponseBean {

	private Subscription currentSubscription;
	private List<Subscription> oldSubscription;

	public SubscriptionResponseBean() {
		// TODO Auto-generated constructor stub
	}

	public SubscriptionResponseBean(Subscription currentSubscription, List<Subscription> oldSubscription) {
		super();
		this.currentSubscription = currentSubscription;
		this.oldSubscription = oldSubscription;
	}

	public Subscription getCurrentSubscription() {
		return currentSubscription;
	}

	public void setCurrentSubscription(Subscription currentSubscription) {
		this.currentSubscription = currentSubscription;
	}

	public List<Subscription> getOldSubscription() {
		return oldSubscription;
	}

	public void setOldSubscription(List<Subscription> oldSubscription) {
		this.oldSubscription = oldSubscription;
	}

	@Override
	public String toString() {
		return "SubscriptionResponseBean [currentSubscription=" + currentSubscription + ", oldSubscription="
				+ oldSubscription + "]";
	}

}
