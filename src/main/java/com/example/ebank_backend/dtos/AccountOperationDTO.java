package com.example.ebank_backend.dtos;

import com.example.ebank_backend.entities.BankAccount;
import com.example.ebank_backend.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id ;
    private Date operationDate ;
    private double amount ;
    private OperationType type ;
    private String description ;
}
