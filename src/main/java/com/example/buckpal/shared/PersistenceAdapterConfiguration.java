package com.example.buckpal.shared;

import com.example.buckpal.account.adapter.out.persistence.AccountMapper;
import com.example.buckpal.account.adapter.out.persistence.AccountPersistenceAdapter;
import com.example.buckpal.account.adapter.out.persistence.AccountRepository;
import com.example.buckpal.account.adapter.out.persistence.ActivityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
public class PersistenceAdapterConfiguration {
    @Bean
    AccountPersistenceAdapter accountPersistenceAdapter(
            AccountRepository accountRepository,
            ActivityRepository activityRepository,
            AccountMapper accountMapper
    ) {
        return new AccountPersistenceAdapter(
                accountRepository,
                activityRepository,
                accountMapper
        );
    }

    @Bean
    AccountMapper accountMapper() {
        return new AccountMapper();
    }
}
