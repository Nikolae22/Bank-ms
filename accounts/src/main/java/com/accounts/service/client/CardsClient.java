package com.accounts.service.client;

import com.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api")
public interface CardsClient {

    @GetExchange(value = "/fetch",accept = "application/json")
    ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);

}
