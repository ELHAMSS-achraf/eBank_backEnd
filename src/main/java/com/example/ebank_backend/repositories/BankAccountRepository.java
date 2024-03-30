package com.example.ebank_backend.repositories;

import com.example.ebank_backend.entities.BankAccount;
import com.example.ebank_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
