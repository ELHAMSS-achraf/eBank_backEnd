package com.example.ebank_backend.dtos;

import com.example.ebank_backend.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CustomerDTO {
    private Long id ;
    private String Name ;
    private String email ;

}
