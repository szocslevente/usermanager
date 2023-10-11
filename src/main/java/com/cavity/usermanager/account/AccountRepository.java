package com.cavity.usermanager.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select s from Account s where s.username = ?1")
    Optional<Account> findAccountByUsername(String username);
}