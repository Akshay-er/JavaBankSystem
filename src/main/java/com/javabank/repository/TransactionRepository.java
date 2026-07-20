package com.javabank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javabank.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions where the account is sender
    List<Transaction> findByFromAccountAccountId(Long accountId);

    // Get all transactions where the account is receiver
    List<Transaction> findByToAccountAccountId(Long accountId);
}