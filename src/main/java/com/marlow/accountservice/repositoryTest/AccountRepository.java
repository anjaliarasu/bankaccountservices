package com.marlow.accountservice.repositoryTest;

import com.marlow.accountservice.entity.Account;
import com.marlow.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByUserIn(List<User> user);

}
