package com.example.ebank_backend.web;

import com.example.ebank_backend.dtos.AccountOperationDTO;
import com.example.ebank_backend.dtos.BankAccountDTO;
import com.example.ebank_backend.entities.CurrentAccount;
import com.example.ebank_backend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class BankAccountRestController {
    BankAccountService bankAccountService ;

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccountDTO(@PathVariable String id){
       return bankAccountService.getBankAccount(id);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> accounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String id){
      return   bankAccountService.accountHistory(id);
    }
}
