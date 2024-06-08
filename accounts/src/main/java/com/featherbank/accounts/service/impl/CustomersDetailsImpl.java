package com.featherbank.accounts.service.impl;

import com.featherbank.accounts.dto.AccountsDto;
import com.featherbank.accounts.dto.CardsDto;
import com.featherbank.accounts.dto.CustomerDetailsDto;
import com.featherbank.accounts.dto.LoansDto;
import com.featherbank.accounts.entity.Accounts;
import com.featherbank.accounts.entity.Customer;
import com.featherbank.accounts.exception.ResourceNotFoundException;
import com.featherbank.accounts.mapper.AccountsMapper;
import com.featherbank.accounts.mapper.CustomerMapper;
import com.featherbank.accounts.repository.AccountsRepository;
import com.featherbank.accounts.repository.CustomerRepository;
import com.featherbank.accounts.service.ICustomersService;
import com.featherbank.accounts.service.client.CardsFeignClient;
import com.featherbank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersDetailsImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
