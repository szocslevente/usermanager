package com.cavity.usermanager.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @PostMapping("/create")
    public void createAccount(@RequestBody Account account){
        accountService.createNewAccount(account);
    }

    @DeleteMapping("/delete")
    public void deleteAccounts(@RequestBody List<Long> accountIds) {
        accountService.deleteAccounts(accountIds);
    }

    @PutMapping("/update")
    public void updateAccount(@RequestBody Account account) {
        accountService.updateAccount(account);
    }
}