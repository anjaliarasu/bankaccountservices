package com.marlow.accountservice.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class AccountRequestDtO {
    Long id;
    List<String> userEmails;
    Double balance;

}
