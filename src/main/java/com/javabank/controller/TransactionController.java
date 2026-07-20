package com.javabank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.javabank.entity.Transaction;
import com.javabank.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/from/{accountId}")
    public List<Transaction> getTransactionsByFromAccount(@PathVariable Long accountId) {
        return transactionService.getTransactionsByFromAccount(accountId);
    }

    @GetMapping("/to/{accountId}")
    public List<Transaction> getTransactionsByToAccount(@PathVariable Long accountId) {
        return transactionService.getTransactionsByToAccount(accountId);
    }
}