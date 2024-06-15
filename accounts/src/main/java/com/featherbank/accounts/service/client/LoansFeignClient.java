package com.featherbank.accounts.service.client;

import com.featherbank.accounts.dto.CardsDto;
import com.featherbank.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loans", fallback = LoansFallback.class)
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", produces = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam  String mobileNumber);
}
