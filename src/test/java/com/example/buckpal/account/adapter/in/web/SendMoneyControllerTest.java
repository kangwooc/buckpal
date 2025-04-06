package com.example.buckpal.account.adapter.in.web;

import com.example.buckpal.account.application.port.in.SendMoneyCommand;
import com.example.buckpal.account.application.port.in.SendMoneyUseCase;
import com.example.buckpal.account.application.service.SendMoneyService;
import com.example.buckpal.account.domain.Account;
import com.example.buckpal.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SendMoneyController.class)
class SendMoneyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SendMoneyUseCase sendMoneyUseCase;

    @Test
    void testSendMoney() throws Exception {
        mockMvc.perform(
                post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}", 1L, 2L, 1000L)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")).andExpect(status().isOk());

        then(sendMoneyUseCase).should().sendMoney(eq(
                new SendMoneyCommand(
                        new Account.AccountId(1L),
                        new Account.AccountId(2L),
                        Money.of(1000L)
                ))
        );

    }
}