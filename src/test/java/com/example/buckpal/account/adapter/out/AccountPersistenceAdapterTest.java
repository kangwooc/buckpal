package com.example.buckpal.account.adapter.out;

import com.example.buckpal.account.adapter.out.persistence.AccountMapper;
import com.example.buckpal.account.adapter.out.persistence.AccountPersistenceAdapter;
import com.example.buckpal.account.adapter.out.persistence.ActivityJpaEntity;
import com.example.buckpal.account.adapter.out.persistence.ActivityRepository;
import com.example.buckpal.account.domain.Account;
import com.example.buckpal.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({
        AccountPersistenceAdapter.class,
        AccountMapper.class,
})
class AccountPersistenceAdapterTest {
    @Autowired
    private AccountPersistenceAdapter accountPersistenceAdapter;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @Sql("AccountPersistenceAdapterTest.sql")
    void loadAccount() {
        Account account = accountPersistenceAdapter.loadAccount(
                new Account.AccountId(1L),
                LocalDateTime.of(2023, 10, 1, 0, 0)
        );

        assertThat(account.getActivityWindow().getActivities()).hasSize(2);
        assertThat(account.getBaselineBalance()).isEqualTo(Money.of(500L));
    }

    @Test
    void updatesActivities() {
        Account account = defaultAccount()
                .withBaselineBalance(Money.of(1000L))
                .withActivityWindow(
                        defaultActivity()
                                .withId(null)
                                .withMoney(Money.of(1L))
                                .build()
                )
                .build();

        accountPersistenceAdapter.updateActivity(account);

        assertThat(activityRepository.findAll()).hasSize(1);

        ActivityJpaEntity savedActivity = activityRepository.findAll().get(0);
        assertThat(savedActivity.getId()).isEqualTo(1L);
    }

}