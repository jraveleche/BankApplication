package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	public ResponseEntity<List<AccountDto>> getAll() {
		// api/accounts
		// Get all accounts

		return ResponseEntity.ok(accountService.getAll());
	}

	public ResponseEntity<AccountDto> get(@PathVariable Long id) {
		// api/accounts/{id}
		// Get accounts by id

		AccountDto result = accountService.getById(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<AccountDto> create(@RequestBody AccountDto accountDto) {
		// api/accounts
		// Create accounts
		return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(accountDto));
	}

	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
		// api/accounts/{id}
		// Update accounts
		AccountDto result = accountService.getById(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		result.setId(accountDto.getId());
		result.setNumber(accountDto.getNumber());
		result.setClientId(accountDto.getClientId());
		result.setActive(accountDto.isActive());
		result.setInitialAmount(accountDto.getInitialAmount());
		AccountDto clientResult = accountService.update(result);
		if (clientResult == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(clientResult);
	}

	public ResponseEntity<AccountDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		// api/accounts/{id}
		// Partial update accounts

		AccountDto result = accountService.partialUpdate(id, partialAccountDto);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Void> delete(@PathVariable Long id) {
		// api/accounts/{id}
		// Delete accounts
		AccountDto result = accountService.getById(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		accountService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
