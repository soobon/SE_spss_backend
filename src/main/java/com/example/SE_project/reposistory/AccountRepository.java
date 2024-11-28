package com.example.SE_project.reposistory;

import com.example.SE_project.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,String> {
    Optional<Account> findByUsername(String username);
}
