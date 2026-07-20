package com.javabank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javabank.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}