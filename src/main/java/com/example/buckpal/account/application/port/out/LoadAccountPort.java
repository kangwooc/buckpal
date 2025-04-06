package com.example.buckpal.account.application.port.out;

import com.example.buckpal.account.domain.Account;

import java.time.LocalDateTime;

public interface LoadAccountPort {
    public Account loadAccount(Account.AccountId accountId, LocalDateTime baselineDate);
}
