package com.javabank.service;

import java.util.List;

import com.javabank.entity.Transaction;

public interface TransactionService {

    // Save Transaction
    Transaction saveTransaction(Transaction transaction);

    // Get All Transactions
    List<Transaction> getAllTransactions();

    // Get Transaction By Id
    Transaction getTransactionById(Long transactionId);

    // Get Transactions by Sender Account
    List<Transaction> getTransactionsByFromAccount(Long accountId);

    // Get Transactions by Receiver Account
    List<Transaction> getTransactionsByToAccount(Long accountId);
}