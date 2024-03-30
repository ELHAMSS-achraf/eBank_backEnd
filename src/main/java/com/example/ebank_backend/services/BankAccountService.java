package com.example.ebank_backend.services;

import com.example.ebank_backend.Exceptions.BalanceNotSufficientException;
import com.example.ebank_backend.Exceptions.BankAccountNotFoundException;
import com.example.ebank_backend.Exceptions.CustomerNotFoundException;
import com.example.ebank_backend.dtos.*;
import com.example.ebank_backend.entities.BankAccount;
import com.example.ebank_backend.entities.CurrentAccount;
import com.example.ebank_backend.entities.Customer;
import com.example.ebank_backend.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {


    public CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentAccountDTO saveCurrentBankAccount(double initialBalance , double overdraft , Long customerId) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance , double interestRate , Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();


    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount , String description) throws BankAccountNotFoundException , BalanceNotSufficientException;
    void credit(String accountId,double amount , String description) throws BankAccountNotFoundException ;
    void transfer (String accountIdSource , String accountIdDestination,double amount ) throws BankAccountNotFoundException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id);

    List<AccountOperationDTO> accountHistory(String accountId);
}
