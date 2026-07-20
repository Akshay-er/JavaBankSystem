package com.javabank.dto;

public class AccountDTO {
	
	private String accountNumber;
	private String accountType;
	private Double balance;
	private Long customerId;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(String accountNumber, String accountType, 
			Double balance, Long customerId) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.customerId = customerId;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCoustomerId(Long coustomerId) {
		this.customerId = customerId;
	}

}
