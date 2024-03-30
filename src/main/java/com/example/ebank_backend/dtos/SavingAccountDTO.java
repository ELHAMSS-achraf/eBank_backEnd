package com.example.ebank_backend.dtos;

import com.example.ebank_backend.entities.AccountOperation;
import com.example.ebank_backend.entities.BankAccount;
import com.example.ebank_backend.entities.Customer;
import com.example.ebank_backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
public class SavingAccountDTO  extends BankAccountDTO {
    private String id ;
    private double balance ;
    private Date createdAt ;
    private AccountStatus status ;
    private CustomerDTO customerDTO ;
    private double interestRate ;

}
