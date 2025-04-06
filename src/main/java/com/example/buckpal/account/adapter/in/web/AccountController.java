package com.example.buckpal.account.adapter.in.web;

import com.example.buckpal.account.application.port.in.GetAccountBalanceQuery;
import com.example.buckpal.account.application.port.in.SendMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class AccountController {
    private final GetAccountBalanceQuery getAccountBalanceQuery;
    private final ListAccountsQuery listAccountsQuery;
    private final LoadAccountQuery loadAccountQuery;

    private final SendMoneyUseCase sendMoneyUseCase;
    private final CreateAccountUseCase createAccountUseCase;

    @GetMapping("/accounts")
    List<AccountResource> listAccounts() {

    }

    @GetMapping("/accounts/{accountId}")
    AccountResource getAccount(@PathVariable("accountId") Long accountId) {

    }

    @GetMapping("/accounts/{accountId}/balance")
    long getAccountBalance(@PathVariable("accountId") Long accountId) {

    }

    @PostMapping("/accounts")
    AccountResource createAccount(@RequestBody AccountResource account) {

    }
}
