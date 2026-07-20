package com.javabank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javabank.entity.Transaction;
import com.javabank.repository.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TransactionServiceImpl implements TransactionService {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {

        logger.info("Saving transaction. Type: {}, Amount: {}",
                transaction.getTransactionType(),
                transaction.getAmount());

        Transaction savedTransaction = transactionRepository.save(transaction);

        logger.info("Transaction saved successfully. ID: {}",
                savedTransaction.getTransactionId());

        return savedTransaction;
    }

    @Override
    public List<Transaction> getAllTransactions() {

        logger.info("Fetching all transactions.");

        List<Transaction> transactions = transactionRepository.findAll();

        logger.info("Total transactions found: {}", transactions.size());

        return transactions;
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {

        logger.info("Fetching transaction with ID: {}", transactionId);

        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> {
                    logger.error("Transaction not found with ID: {}", transactionId);
                    return new RuntimeException("Transaction Not Found");
                });
    }

    @Override
    public List<Transaction> getTransactionsByFromAccount(Long accountId) {

        logger.info("Fetching outgoing transactions for Account ID: {}", accountId);

        List<Transaction> transactions =
                transactionRepository.findByFromAccountAccountId(accountId);

        logger.info("Total outgoing transactions found: {}",
                transactions.size());

        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByToAccount(Long accountId) {

        logger.info("Fetching incoming transactions for Account ID: {}", accountId);

        List<Transaction> transactions =
                transactionRepository.findByToAccountAccountId(accountId);

        logger.info("Total incoming transactions found: {}",
                transactions.size());

        return transactions;
    }
}