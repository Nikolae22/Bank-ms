package com.accounts.service.client;

import com.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api")
public interface LoansClient {

    @GetExchange("/fetch")
    ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
