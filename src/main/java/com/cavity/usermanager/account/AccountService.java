package com.cavity.usermanager.account;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts(){
        return accountRepository.findAll();
    }

    public void createNewAccount(Account account) {
        Optional<Account> accountByUsername = accountRepository
                .findAccountByUsername(account.getUsername());
        if (accountByUsername.isPresent()){
            throw new IllegalStateException("username taken");
        }

        if (!validateUsername(account.getUsername())) {
            throw new IllegalStateException("username can contain only characters from the english alphabet");
        }

        if (!validatePin(account.getPin())) {
            throw new IllegalStateException("pin can contain only characters from the english alphabet and must be at least 4 characters long");
        }

        if (!validateRole(account.getRole())){
            throw new IllegalStateException("Role can only be 'User' or 'Engineer'");
        }
        accountRepository.save(account);
    }

    public void deleteAccounts(List<Long> accountIds) {
        for (Long accountId : accountIds) {
            boolean exists = accountRepository.existsById(accountId);
            if (!exists) {
                throw new IllegalStateException("Account with id " + accountId + " does not exist");
            }
            accountRepository.deleteById(accountId);
        }
    }
    @Transactional
    public void updateAccount(Account updatedAccount) {
        Long accountId = Long.valueOf(updatedAccount.getAccountId());
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account with id " + accountId + " does not exist"));

        String pin = updatedAccount.getPin();
        String role = updatedAccount.getRole();

        if (pin != null && !pin.isEmpty() && !pin.equals(account.getPin())) {
            if (!validatePin(pin)) {
                throw new IllegalStateException("Pin can only contain characters from the English alphabet and must be at least 4 characters long");
            }
            account.setPin(pin);
        }

        if (role != null && !role.isEmpty() && !role.equals(account.getRole())) {
            if (!validateRole(role)) {
                throw new IllegalStateException("Role can only be User or Engineer");
            }
            account.setRole(role);
        }
    }


    private static boolean validateUsername(String username){
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private static boolean validatePin(String pin){
        String regex = "^[a-zA-Z0-9]{4,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pin);
        return matcher.matches();
    }

    private static boolean validateRole(String role) {
        // role should be ENUM
        return "User".equals(role) || "Engineer".equals(role);
    }
}