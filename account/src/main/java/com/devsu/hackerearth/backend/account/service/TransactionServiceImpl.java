package com.devsu.hackerearth.backend.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<TransactionDto> getAll() {
        // Get all transactions
        List<TransactionDto> accounts = transactionRepository
                .findAll().stream().map(e -> transactionToDto(e))
                .collect(Collectors.toList());
        return accounts;
    }

    @Override
    public TransactionDto getById(Long id) {
        // Get transactions by id
        Optional<Transaction> optional = transactionRepository.findById(id);
        return transactionToDto(optional.get());
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {
        // Create transaction
        transactionRepository.save(dtoToTransaction(transactionDto));
        return transactionToDto(transactionRepository.save(dtoToTransaction(transactionDto)));
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart,
            Date dateTransactionEnd) {
        // Report

        if (clientId == null || dateTransactionEnd == null || dateTransactionStart == null) {
            return new ArrayList<>();
        }

        List<Account> accounts = accountRepository.findByClientId(clientId);
        List<BankStatementDto> bankStatement = new ArrayList<>();
        accounts.forEach(e -> {
            List<Transaction> transactions = transactionRepository.findAllByAccountIdAndDateBetweenOrderByDateDesc(
                    e.getId(), dateTransactionStart, dateTransactionEnd);
            transactions.forEach(t -> {
                bankStatement.add(new BankStatementDto(t.getDate(), String.valueOf(e.getClientId()), e.getNumber(),
                        e.getType(), e.getInitialAmount(), e.isActive(), t.getType(), t.getAmount(), t.getBalance()));
            });
        });
        return bankStatement;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

    private TransactionDto transactionToDto(Transaction transaction) {
        return new TransactionDto(transaction.getId(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBalance(),
                transaction.getAccountId());
    }

    private Transaction dtoToTransaction(TransactionDto dto) {
        final Transaction transaction = new Transaction();
        transaction.setId(dto.getId());
        transaction.setDate(dto.getDate());
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setBalance(dto.getBalance());
        transaction.setAccountId(dto.getAccountId());
        return transaction;
    }

}
