package com.example.buckpal.account.application.port.out;

import com.example.buckpal.account.domain.Account;

public interface UpdateAccountStatePort {
    public void updateAccountState(Account account);
}
