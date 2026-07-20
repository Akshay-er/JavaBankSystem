package com.javabank.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javabank.entity.Account;
import com.javabank.entity.Customer;
import com.javabank.repository.AccountRepository;
import com.javabank.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;
import com.javabank.exception.AccountNotFoundException;
import com.javabank.exception.CustomerNotFoundException;

import java.time.LocalDateTime;

import com.javabank.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    private static final Logger logger =
            LoggerFactory.getLogger(AccountServiceImpl.class);
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private TransactionService transactionService;
    

    @Override
    public Account createAccount(Account account) {

        logger.info("Creating new account.");

        Long customerId = account.getCustomer().getCustomerId();

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", customerId);
                    return new CustomerNotFoundException("Customer Not Found");
                });

        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);

        logger.info(
        	    "Account created successfully. Account ID: {}, Customer ID: {}",
        	    savedAccount.getAccountId(),
        	    customerId
        	);
        return savedAccount;
    }
    @Override
    public Account depositMoney(Long accountId, Double amount) {

        logger.info("Deposit request. Account ID: {}, Amount: {}", accountId, amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    logger.error("Account not found with ID: {}", accountId);
                    return new AccountNotFoundException("Account Not Found");
                });

        if (amount <= 0) {
            logger.warn("Invalid deposit amount: {}", amount);
            throw new RuntimeException("Deposit amount must be greater than zero");
        }

        account.setBalance(account.getBalance() + amount);
        
        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setFromAccount(null);
        transaction.setToAccount(account);

        transactionService.saveTransaction(transaction);
        
        logger.info("Deposit successful. Updated Balance: {}", account.getBalance());

        return accountRepository.save(account);
    }
    
    
    @Override
    public Account withdrawMoney(Long accountId, Double amount) {
    	logger.info("Withdraw request. Account ID: {}, Amount: {}", accountId, amount);

    	Account account = accountRepository.findById(accountId)
    	        .orElseThrow(() -> {
    	            logger.error("Account not found with ID: {}", accountId);
    	            return new AccountNotFoundException("Account Not Found");
    	        });
        
        
        
        
        if (amount <= 0) {
        	logger.warn("Invalid withdraw amount: {}", amount);
            throw new RuntimeException("Withdraw amount must be greater than zero");
            
        }
        
        
        if (account.getBalance() < amount) {
        	logger.warn("Insufficient balance. Account ID: {}", accountId);
            throw new RuntimeException("Insufficient Balance");
            
        }
     
        
        
        account.setBalance(account.getBalance() - amount);
        
        logger.info("Withdrawal successful. Remaining Balance: {}", account.getBalance());
        
        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setFromAccount(account);
        transaction.setToAccount(null);

        transactionService.saveTransaction(transaction);

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {

        logger.info("Fetching all accounts.");

        List<Account> accounts = accountRepository.findAll();

        logger.info("Total accounts found: {}", accounts.size());

        return accounts;
    }

    @Override
    public Account getAccountById(Long accountId) {
    	logger.info("Fetching account with ID: {}", accountId);
    	return accountRepository.findById(accountId)
    	        .orElseThrow(() -> {
    	            logger.error("Account not found with ID: {}", accountId);
    	            return new AccountNotFoundException("Account Not Found");
    	        });
        
    }

    @Override
    public void deleteAccount(Long accountId) {
    	logger.info("Deleting account with ID: {}", accountId);
    
    	Account account = accountRepository.findById(accountId)
    	        .orElseThrow(() -> {
    	            logger.error("Account not found with ID: {}", accountId);
    	            return new AccountNotFoundException("Account Not Found");
    	        });

        accountRepository.delete(account);
        logger.info("Account deleted successfully.");
    }
    @Override
    public Account updateAccount(Long accountId, Account account) {

        Account existingAccount =
                accountRepository.findById(accountId)
                .orElseThrow(() ->
                    new RuntimeException("Account Not Found"));


        existingAccount.setAccountNumber(
                account.getAccountNumber()
        );

        existingAccount.setAccountType(
                account.getAccountType()
        );

        existingAccount.setBalance(
                account.getBalance()
        );


        Long customerId = account.getCustomer().getCustomerId();

        Customer customer =
                customerRepository.findById(customerId)
                .orElseThrow(() ->
                    new CustomerNotFoundException("Customer Not Found"));


        existingAccount.setCustomer(customer);


        return accountRepository.save(existingAccount);
    }
    @Override
    @Transactional
    public Account transferMoney(Long fromAccountId,
                                 Long toAccountId,
                                 Double amount) {
    	logger.info("Transfer request. From: {} To: {} Amount: {}",
    	        fromAccountId,
    	        toAccountId,
    	        amount);
    	
    	
    	Account fromAccount = accountRepository.findById(fromAccountId)
    	        .orElseThrow(() -> {
    	            logger.error("Sender account not found: {}", fromAccountId);
    	            return new AccountNotFoundException("Sender Account Not Found");
    	        });

    	
    	Account toAccount = accountRepository.findById(toAccountId)
    	        .orElseThrow(() -> {
    	            logger.error("Receiver account not found: {}", toAccountId);
    	            return new AccountNotFoundException("Receiver Account Not Found");
    	        });

    	
    	if (amount <= 0) {
    	    logger.warn("Invalid transfer amount: {}", amount);
    	    throw new RuntimeException("Transfer amount must be greater than zero");
    	}
        
        
        if (fromAccount.getBalance() < amount) {
        	logger.warn("Insufficient balance in sender account: {}", fromAccountId);
            throw new RuntimeException("Insufficient Balance");
            
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        logger.info(
            "Transfer completed successfully. From: {} To: {} Amount: {}",
            fromAccountId,
            toAccountId,
            amount
        );
        
        

        Account updatedFromAccount = accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

    
        
        Transaction transaction = new Transaction();

        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setFromAccount(updatedFromAccount);
        transaction.setToAccount(toAccount);

        transactionService.saveTransaction(transaction);
  
        return updatedFromAccount;
    }
}