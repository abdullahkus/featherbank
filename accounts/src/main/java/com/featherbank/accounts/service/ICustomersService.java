package com.featherbank.accounts.service;

import com.featherbank.accounts.dto.CustomerDetailsDto;

public interface ICustomersService {
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
