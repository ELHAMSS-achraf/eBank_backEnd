package com.example.ebank_backend.entities;

import com.example.ebank_backend.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.util.Date;
import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor @Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE" , length = 4 )
public abstract class BankAccount {
    @Id
    private String id ;
    private double balance ;
    private Date createdAt ;
    @Enumerated(EnumType.STRING)
    private AccountStatus status ;
    @ManyToOne
    private Customer customer ;
    @OneToMany (mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations ;
}
