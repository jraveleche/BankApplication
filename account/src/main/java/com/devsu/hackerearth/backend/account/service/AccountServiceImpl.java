package com.devsu.hackerearth.backend.account.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> getAll() {
        // Get all accounts
        List<AccountDto> accounts = accountRepository
                .findAll().stream().map(e -> accountToDto(e))
                .collect(Collectors.toList());
        return accounts;
    }

    @Override
    public AccountDto getById(Long id) {
        // Get accounts by id
        Optional<Account> optional = accountRepository.findById(id);
        return accountToDto(optional.get());
    }

    @Override
    public AccountDto create(AccountDto accountDto) {
        // Create account
        accountRepository.save(dtoToAccount(accountDto));
        return accountToDto(accountRepository.save(dtoToAccount(accountDto)));
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        // Update account
        Optional<Account> optional = accountRepository.findById(accountDto.getId());
        if (optional.isEmpty()) {
            return null;
        }
        Account account = optional.get();
        account.setId(accountDto.getId());
        account.setNumber(accountDto.getNumber());
        account.setClientId(accountDto.getClientId());
        account.setActive(accountDto.isActive());
        account.setInitialAmount(accountDto.getInitialAmount());
        Account accountResult = accountRepository.save(account);
        return accountToDto(accountResult);
    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        // Partial update account

        Optional<Account> optional = accountRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        Account account = optional.get();
        account.setActive(partialAccountDto.isActive());
        return accountToDto(accountRepository.save(account));
    }

    @Override
    public void deleteById(Long id) {
        // Delete account
    }

    private AccountDto accountToDto(Account account) {
        return new AccountDto(account.getId(),
                account.getNumber(),
                account.getType(),
                account.getInitialAmount(),
                account.isActive(),
                account.getClientId());
    }

    private Account dtoToAccount(AccountDto dto) {
        final Account account = new Account();
        account.setId(dto.getId());
        account.setNumber(dto.getNumber());
        account.setClientId(dto.getClientId());
        account.setActive(dto.isActive());
        account.setInitialAmount(dto.getInitialAmount());
        return account;
    }

}
