package com.javabank.service;

import java.util.List;



import com.javabank.entity.Account;

public interface AccountService {
	
	Account createAccount(Account account);
	
	Account depositMoney(Long accountId, Double amount);
	
	Account withdrawMoney(Long accountId, Double amount);
	
	List<Account> getAllAccounts();
	
	Account getAccountById(Long accountId);
	
	Account transferMoney(Long fromAccountId, Long toAccountId, Double amount);
	
	void deleteAccount(Long accountId);
	
	
	 Account updateAccount(Long accountId, Account account);
	
	
	
}
