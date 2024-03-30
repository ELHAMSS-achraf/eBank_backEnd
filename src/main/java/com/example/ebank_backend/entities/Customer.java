package com.example.ebank_backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor @Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String Name ;
    private String email ;
    @OneToMany(mappedBy = "customer")
    private List<BankAccount> bankAccounts ;

}
