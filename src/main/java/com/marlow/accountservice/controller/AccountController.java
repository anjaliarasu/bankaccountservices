package com.marlow.accountservice.controller;

import com.marlow.accountservice.DTO.AccountRequestDtO;
import com.marlow.accountservice.DTO.MyMsg;
import com.marlow.accountservice.entity.Account;
import com.marlow.accountservice.serviceTest.AccountService;
import com.marlow.accountservice.serviceTest.KafkaProducerService;
import com.marlow.accountservice.serviceTest.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/{userId}/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    KafkaProducerService kafkaProducerService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Optional<Account> findById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @PathVariable String userId, @RequestBody AccountRequestDtO account) {

        Account account1=accountService.validateRequestAndConvertEntity(account);
            Map<String, Object> body = new HashMap<>();
            if (account1 != null) {
                body.put("message", "Success: Account details successfully saved!");
                body.put("data",account1);
                MyMsg myMsg=new MyMsg("create-account", account.getId(), userId);
                kafkaProducerService.send("topic-name", myMsg);
                return new ResponseEntity<>(body, HttpStatus.CREATED);
            } else {
                body.put("message", "Fail: Email already exists!");
                return new ResponseEntity<>(body, HttpStatus.CONFLICT);
            }

        }

    @ResponseStatus(HttpStatus.OK) // 201
    @PutMapping (produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @PathVariable String userId, @RequestBody AccountRequestDtO account) {

        Account account1=accountService.validateRequestAndConvertEntity(account);
        Map<String, Object> body = new HashMap<>();
        if (account1 != null) {
            body.put("message", "Success: Account details successfully updated!");
            MyMsg myMsg=new MyMsg("update-account", account.getId(), userId);
            kafkaProducerService.send("topic-name", myMsg);
            return new ResponseEntity<>(body, HttpStatus.CREATED);
        } else {
            body.put("message", "Fail: Email already exists!");
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);
        }

    }




    // update a user
    /*@PutMapping
    public Account save(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }*/

    // delete a user
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        accountService.deleteById(id);
    }


    @PostMapping("/{id}/deposit")
    public Account deposit(@Valid @PathVariable String userId,@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        Account account= accountService.deposit(id, amount);
        if(account!=null) {
            MyMsg myMsg = new MyMsg("deposit", account.getId(), userId);
            kafkaProducerService.send("topic-name", myMsg);
        }
        return account;
    }

    @PostMapping("/{id}/withdraw")
    public Account withdraw(@Valid @PathVariable String userId,@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        Account account=accountService.withdraw(id, amount);
        if(account!=null) {
            MyMsg myMsg = new MyMsg("withdraw", account.getId(), userId);
            kafkaProducerService.send("topic-name", myMsg);
        }
        return account;
    }

}
