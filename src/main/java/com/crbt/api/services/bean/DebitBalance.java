package com.crbt.api.services.bean;

import java.io.Serializable;

public class DebitBalance implements Serializable{

	private static final long serialVersionUID = -3911054590866911976L;
	
	private String conversationId;
	private String transactionId;
	private String originatingAddress;
	private String destinationAddress;
	private String chargingAddress;
	private Long amount;
	
	public DebitBalance() {}

	public DebitBalance(String conversationId, String transactionId, String originatingAddress,
			String destinationAddress, String chargingAddress, Long amount) {
		super();
		this.conversationId = conversationId;
		this.transactionId = transactionId;
		this.originatingAddress = originatingAddress;
		this.destinationAddress = destinationAddress;
		this.chargingAddress = chargingAddress;
		this.amount = amount;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOriginatingAddress() {
		return originatingAddress;
	}

	public void setOriginatingAddress(String originatingAddress) {
		this.originatingAddress = originatingAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getChargingAddress() {
		return chargingAddress;
	}

	public void setChargingAddress(String chargingAddress) {
		this.chargingAddress = chargingAddress;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "DebitBalance [conversationId=" + conversationId + ", transactionId=" + transactionId
				+ ", originatingAddress=" + originatingAddress + ", destinationAddress=" + destinationAddress
				+ ", chargingAddress=" + chargingAddress + ", amount=" + amount + "]";
	}
	
	
}
