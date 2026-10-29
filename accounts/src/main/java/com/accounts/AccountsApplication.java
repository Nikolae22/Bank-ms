package com.accounts;

import com.accounts.dto.AccountsContactInfoDto;
import com.accounts.service.client.CardsClient;
import com.accounts.service.client.LoansClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.service.registry.ImportHttpServices;

@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@ImportHttpServices(group = "loans",types = LoansClient.class)
@ImportHttpServices(group = "cards",types = CardsClient.class)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
