package com.example.ebank_backend.services;

import com.example.ebank_backend.Exceptions.BalanceNotSufficientException;
import com.example.ebank_backend.Exceptions.BankAccountNotFoundException;
import com.example.ebank_backend.Exceptions.CustomerNotFoundException;
import com.example.ebank_backend.dtos.*;
import com.example.ebank_backend.entities.*;
import com.example.ebank_backend.enums.OperationType;
import com.example.ebank_backend.mappers.BankAccountMapperImpl;
import com.example.ebank_backend.repositories.AccountOperationRepository;
import com.example.ebank_backend.repositories.BankAccountRepository;
import com.example.ebank_backend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor @Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountRepository bankAccountRepository ;
    private BankAccountMapperImpl dtoMapper ;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("saving new customer");

        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft , Long customerId) throws CustomerNotFoundException {

        CurrentAccount currentAccount = new CurrentAccount();
        Customer customer = customerRepository.findById(customerId).orElseThrow(null);

        if(customer==null) {
            throw new CustomerNotFoundException("customer not found");
        }
        else {
            currentAccount.setCustomer(customer);
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setBalance(initialBalance);
            currentAccount.setOverDraft(overDraft);

            CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);
            return dtoMapper.fromCurrentAccount(savedCurrentAccount) ;
        }



    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate , Long customerId) throws CustomerNotFoundException {
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = customerRepository.findById(customerId).orElseThrow(null);

        if(customer==null) {
            throw new CustomerNotFoundException("customer not found");
        }
        else {
            savingAccount.setCustomer(customer);
            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setBalance(initialBalance);
            savingAccount.setInterestRate(interestRate);

            SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
            return dtoMapper.fromSavingAccount(savedSavingAccount);
        }
    }


    @Override
    public List<CustomerDTO> listCustomers() {

        List<CustomerDTO> listCustomerDto = customerRepository.findAll().stream().map(customer ->
                dtoMapper.fromCustomer(customer)).collect(Collectors.toList());

    return listCustomerDto ;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() ->
                new BankAccountNotFoundException("Bank Account not found"));

        if(bankAccount instanceof SavingAccount ){
            SavingAccount savingAccount =(SavingAccount)bankAccount;
            return dtoMapper.fromSavingAccount(savingAccount);
        }else{
            CurrentAccount currentAccount = (CurrentAccount)bankAccount;
            return dtoMapper.fromCurrentAccount(currentAccount);
        }

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException , BalanceNotSufficientException{
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() ->
                new BankAccountNotFoundException("Bank Account not found"));

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());

        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()+amount);

        bankAccountRepository.save(bankAccount);


    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException  {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() ->
                new BankAccountNotFoundException("Bank Account not found"));

        if(amount>bankAccount.getBalance()) throw new BalanceNotSufficientException("Not sufficient balance");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());

        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException , BalanceNotSufficientException {
     debit(accountIdDestination,amount,"Transfer to "+ accountIdDestination);
     credit(accountIdDestination,amount,"Transfer from "+ accountIdSource);

    }
    @Override
    public List<BankAccountDTO> bankAccountList(){

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOList = bankAccountList.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentAccount(currentAccount);
            }
        }).collect(Collectors.toList());
return bankAccountDTOList ;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId){
        Customer customer = customerRepository.findById(customerId)
                        .orElseThrow(() -> new CustomerNotFoundException("customer not found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("updating customer");

        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> list = accountOperationRepository.findByBankAccountId(accountId);
        List<AccountOperationDTO> operationList = list.stream()
                        .map(accountOperation -> dtoMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());

return operationList;
    }



}
