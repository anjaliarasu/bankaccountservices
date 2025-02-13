package com.marlow.accountservice.serviceTest;

import com.marlow.accountservice.DTO.AccountRequestDtO;
import com.marlow.accountservice.entity.Account;
import com.marlow.accountservice.entity.User;
import com.marlow.accountservice.repositoryTest.AccountRepository;
import com.marlow.accountservice.repositoryTest.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("AccountService")
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Account> findById(Long id) {
        Optional<Account> account=accountRepository.findById(id);
        if(account.isPresent())
            log.info("Account found successfuly for account id "+account.get().getId());
        else
            log.error("Account not found for "+account.get().getId());

        return accountRepository.findById(id);
    }

    public Account findByUser(Account account) {
        return accountRepository.findByUserIn((account.getUser()));
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    public Account validateRequestAndConvertEntity(AccountRequestDtO account) {
        List<User> usersInput = userRepository.findAllByEmailIsIn(account.getUserEmails());

        //Check if passed users are already registered in the system
        Boolean isUsersExists = usersInput.stream().map(User::getEmail).anyMatch(
                (String s) -> {
                    return account.getUserEmails().contains(s);
                });

        if (!isUsersExists) {
            log.error("One or more emails passed in is not registered in the system");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more emails passed in is not registered in the system! Please register user first!");
        }else {
            //find if account exists for passed users
            Account accountUsers = accountRepository.findByUserIn((usersInput));
            Account account1 = null;
            //Allow create account only if there is no account exists for that user
            if (account.getId() == null) {

                if (accountUsers == null) {
                    account1 = new Account();
                    account1.setAccountBalance(account.getBalance());
                    for (User user : usersInput)
                        user.setAccount(account1);
                    account1.setUser(usersInput);

                    return accountRepository.save(account1);
                }
            }
            //Allow update account even if there is no account exists for that user
            else {
                //update account
                if (accountUsers == null) {

                    Optional<Account> account2 = accountRepository.findById(account.getId());
                    if (account2.isPresent())
                        account1 = account2.get();
                    assert account1 != null;
                    account1.setAccountBalance(account.getBalance());
                    for (User user : usersInput)
                        user.setAccount(account1);
                    account1.setUser(usersInput);

                    return accountRepository.save(account1);
                } else {
                    for (User user : usersInput)
                        user.setAccount(accountUsers);
                    accountUsers.setUser(usersInput);
                    accountUsers.setAccountBalance(account.getBalance());
                    return accountRepository.save(accountUsers);
                }
            }
        }
        log.error("One or more emails passed in is already associated with account");
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more emails passed in is already associated with account");
    }

    public synchronized Account deposit(Long id, double amount) {
        Account updateDeposit = null;
        Account account = findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
            account.setAccountBalance(account.getAccountBalance() + amount);
            try {
               updateDeposit= accountRepository.save(account);
                if(updateDeposit!=null)
                    log.info("Amount "+amount+" deposited succesfully");
            }
            catch (Exception e)
            {
                log.error("Failed to deposit:"+e.getMessage());
            }
return updateDeposit;
    }

    public synchronized Account withdraw(Long id, double amount) {
        Account updateWithdraw = null;
        Account account = findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getAccountBalance() < amount) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setAccountBalance(account.getAccountBalance() - amount);
        try {
            updateWithdraw = accountRepository.save(account);
            if (updateWithdraw != null)
                log.info("Amount " + amount + " withdrawn succesfully");
        } catch (Exception e) {
            log.error("Failed to withdraw:" + e.getMessage());
        }
        return updateWithdraw;
    }
    }

