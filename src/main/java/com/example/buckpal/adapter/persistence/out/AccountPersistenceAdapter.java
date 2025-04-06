package com.example.buckpal.adapter.persistence.out;

import com.example.buckpal.account.application.port.out.LoadAccountPort;
import com.example.buckpal.account.application.port.out.UpdateAccountStatePort;
import com.example.buckpal.account.domain.Account;
import com.example.buckpal.adapter.persistence.AccountJpaEntity;
import com.example.buckpal.adapter.persistence.AccountRepository;
import com.example.buckpal.adapter.persistence.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(
            Account.AccountId accountId,
            LocalDateTime baselineDate
    ) {
        AccountJpaEntity account = accountRepository.findById(accountId.getValue())
                .orElseThrow();
    }
}
