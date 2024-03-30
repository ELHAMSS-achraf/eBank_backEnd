package com.example.ebank_backend.repositories;

import com.example.ebank_backend.entities.AccountOperation;
import com.example.ebank_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    public List<AccountOperation> findByBankAccountId(String accountId);

}
