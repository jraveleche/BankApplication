package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class TransactionDto {

	private Long id;
    private Date date;
	private String type;
	private double amount;
	private double balance;
	private Long accountId;
}
