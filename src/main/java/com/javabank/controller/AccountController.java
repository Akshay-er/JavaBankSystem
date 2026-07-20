package com.javabank.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.javabank.entity.Account;
import com.javabank.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    // Create Account
    @Operation(summary = "Create a new account")
    @PostMapping("/create")
    public Account createAccount(@Valid @RequestBody Account account) {

        logger.info("REST Request: Create Account");

        Account savedAccount = accountService.createAccount(account);

        logger.info("REST Response: Account created with ID: {}",
                savedAccount.getAccountId());

        return savedAccount;
    }
    
    // Get Account By ID
    @Operation(summary = "Get Account By ID")
    @GetMapping("/{accountId}")
    public Account getAccountById(@PathVariable Long accountId) {

        logger.info("REST Request: Get Account By ID: {}", accountId);

        Account account = accountService.getAccountById(accountId);

        logger.info("REST Response: Account fetched successfully. ID: {}", accountId);

        return account;
    }    
    // Get All Accounts
    @Operation(summary = "Get All Accounts")
    @GetMapping("/all")
    public List<Account> getAllAccounts() {

        logger.info("REST Request: Get All Accounts");

        List<Account> accounts = accountService.getAllAccounts();

        logger.info("REST Response: Total Accounts: {}", accounts.size());

        return accounts;
    }
    @Operation(summary = "Deposit Money")
    @PutMapping("/deposit/{accountId}")
    public Account depositMoney(@PathVariable Long accountId,
                                @RequestParam Double amount) {

        logger.info("REST Request: Deposit. Account ID: {}, Amount: {}",
                accountId, amount);

        Account account = accountService.depositMoney(accountId, amount);

        logger.info("REST Response: Deposit completed successfully. Account ID: {}", accountId);
        return account;
    }
    @Operation(summary = "Withdraw Money")
    @PutMapping("/withdraw/{accountId}")
    public Account withdrawMoney(@PathVariable Long accountId,
                                 @RequestParam Double amount) {

        logger.info("REST Request: Withdraw. Account ID: {}, Amount: {}",
                accountId, amount);

        Account account = accountService.withdrawMoney(accountId, amount);

        logger.info("REST Response: Withdrawal completed successfully. Account ID: {}", accountId);

        return account;
    }
    @Operation(summary = "Transfer Money")
    @PutMapping("/transfer")
    public Account transferMoney(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam Double amount) {

        logger.info(
                "REST Request: Transfer. From: {} To: {} Amount: {}",
                fromAccountId,
                toAccountId,
                amount);

        Account account = accountService.transferMoney(
                fromAccountId,
                toAccountId,
                amount);

        logger.info("REST Response: Transfer completed successfully.");

        return account;
    }

    // Delete Account
    @Operation(summary = "Delete Account")
    @DeleteMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable Long accountId) {

        logger.info("REST Request: Delete Account ID: {}", accountId);

        accountService.deleteAccount(accountId);

        logger.info("REST Response: Account deleted successfully.");

        return "Account Deleted Successfully";
    }
    @Operation(summary = "Update Account")
    @PutMapping("/{accountId}")
    public Account updateAccount(
            @PathVariable Long accountId,
            @RequestBody Account account) {

        logger.info("REST Request: Update Account ID: {}", accountId);

        return accountService.updateAccount(accountId, account);
    }

}