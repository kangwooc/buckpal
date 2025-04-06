package com.example.buckpal.account;

import com.example.buckpal.account.domain.Account;
import com.example.buckpal.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendMoneySystemTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql("SendMoneySystemTest.sql")
    void sendMoney() {
        Money initialSourceBalance = sourceAccount().calculateBalance();
        Money initialTargetBalance = targetAccount().calculateBalance();

        ResponseEntity response = whenSendMoney(
                sourceAccount().getId(),
                targetAccount().getId(),
                Money.of(1000L)
        );

        then(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        then(sourceAccount().calculateBalance())
                .isEqualTo(initialSourceBalance.minus(Money.of(1000L)));
        then(targetAccount().calculateBalance())
                .isEqualTo(initialTargetBalance.plus(Money.of(1000L)));
    }

    private ResponseEntity whenSendMoney(
            Account.AccountId sourceAccountId,
            Account.AccountId targetAccountId,
            Money amount,
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(null, headers);

        return restTemplate.postForEntity(
                "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                request,
                ResponseEntity.class,
                sourceAccountId.getValue(),
                targetAccountId.getValue(),
                amount.getAmount()
        );
    }
}
