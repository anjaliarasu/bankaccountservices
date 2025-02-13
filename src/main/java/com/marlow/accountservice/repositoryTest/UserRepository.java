package com.marlow.accountservice.repositoryTest;

import com.marlow.accountservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByName(String name);

    User findByEmail(String email);

    List<User> findAllByEmailIsIn(List<String> emails);
}
