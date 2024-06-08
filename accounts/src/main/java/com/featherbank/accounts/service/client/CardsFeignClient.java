package com.featherbank.accounts.service.client;

import com.featherbank.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards")
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", produces = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam  String mobileNumber);
}
