package com.example.buckpal.account.application.port.in;

import com.example.buckpal.account.domain.Account;
import com.example.buckpal.account.domain.Money;
import com.example.buckpal.shared.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {
    @NotNull
    private final Account.AccountId sourceAccountId;
    @NotNull
    private final Account.AccountId targetAccountId;
    @NotNull
    private final Money money;

    public SendMoneyCommand(Account.AccountId sourceAccountId, Account.AccountId targetAccountId, Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.validateSelf();
    }
}
