package com.marlow.accountservice.DTO;

import com.marlow.accountservice.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class AccountResponse {

    Long id;
    List<User> users;
    Double balance;

}
