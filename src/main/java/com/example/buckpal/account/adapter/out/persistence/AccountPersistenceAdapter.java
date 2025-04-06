package com.example.buckpal.account.adapter.out.persistence;

import com.example.buckpal.account.application.port.out.LoadAccountPort;
import com.example.buckpal.account.application.port.out.UpdateAccountStatePort;
import com.example.buckpal.account.domain.Account;
import com.example.buckpal.account.domain.Activity;
import com.example.buckpal.shared.PersistenceAdapter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
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
                .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(accountId.getValue(), baselineDate);

        Long withdrawalBalance = activityRepository.getWithdrawalBalanceUntil(accountId.getValue(), baselineDate);

        Long depositBalance = activityRepository.getDepositBalanceUntil(accountId.getValue(), baselineDate);

        return accountMapper.mapToDomainEntity(account, activities, withdrawalBalance, depositBalance);
    }

    private Long orZero(Long value) {
        return value == null ? 0L : value;
    }

    @Override
    public void updateAccountState(Account account) {
        for (Activity activity : account.getActivityWindow().getActivities()) {
            if (activity.getId() == null) {
                activityRepository.save(accountMapper.mapToJpaEntity(activity));
            }
        }
    }
}