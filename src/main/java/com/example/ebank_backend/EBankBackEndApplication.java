package com.example.ebank_backend;

import com.example.ebank_backend.dtos.CurrentAccountDTO;
import com.example.ebank_backend.dtos.CustomerDTO;
import com.example.ebank_backend.dtos.SavingAccountDTO;
import com.example.ebank_backend.entities.AccountOperation;
import com.example.ebank_backend.entities.CurrentAccount;
import com.example.ebank_backend.entities.Customer;
import com.example.ebank_backend.entities.SavingAccount;
import com.example.ebank_backend.enums.AccountStatus;
import com.example.ebank_backend.enums.OperationType;
import com.example.ebank_backend.repositories.AccountOperationRepository;
import com.example.ebank_backend.repositories.BankAccountRepository;
import com.example.ebank_backend.repositories.CustomerRepository;
import com.example.ebank_backend.services.BankAccountService;
import com.example.ebank_backend.services.BankAccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankBackEndApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Achraf", "Nizar", "Naima").forEach(name ->{
                        CustomerDTO customer = new CustomerDTO();
                        customer.setName(name);
                        customer.setEmail(name+"@gmail.com");
                        bankAccountService.saveCustomer(customer);
                    }

                    );
        bankAccountService.listCustomers().forEach(customer -> {
            bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000,customer.getId());
            bankAccountService.saveSavingBankAccount(Math.random()*9000,4.5,customer.getId());
        });
        bankAccountService.bankAccountList().forEach(bankAccount -> {
            for (int i = 0; i < 10; i++) {
                String accountId ;
                if(bankAccount instanceof SavingAccountDTO){
                    accountId= ((SavingAccountDTO) bankAccount).getId();
                }else {
                    accountId=  ((CurrentAccountDTO) bankAccount).getId();
                }
                bankAccountService.credit(accountId, 10000+Math.random()*4000,"credit");
                bankAccountService.debit(accountId, 1000+Math.random()*9000,"debit");


            }
        });
        };
    }

}
