package com.example.buckpal.account.application.service;

import com.example.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.buckpal.account.domain.Account;
import com.example.buckpal.account.domain.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class SendMoneyServiceTest {
    @Test
    void transactionSucceeds() {
        Account sourceAccount = givenSourceAccount();
        Account targetAccount = givenTargetAccount();

        givenWithdrawalWillSucceeds(sourceAccount);
        givenDepositWillSucceeds(targetAccount);

        Money money = Money.of(500L);

        SendMoneyCommand command = new SendMoneyCommand(
                sourceAccount.getId(),
                targetAccount.getId(),
                money
        );

        boolean success = sendMoneyService.sendMoney(command);
        assertTrue(success);

        Account.AccountId sourceAccountId = sourceAccount.getId();
        Account.AccountId targetAccountId = targetAccount.getId();

        then(accountLock).should().lockAccount(sourceAccountId);
        then(accountLock).should().lockAccount(targetAccountId);

    }
}