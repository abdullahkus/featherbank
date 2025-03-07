package com.featherbank.accounts.controller;

import com.featherbank.accounts.dto.CustomerDetailsDto;
import com.featherbank.accounts.dto.ErrorResponseDto;
import com.featherbank.accounts.service.ICustomersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST APIs for Customers in Feather Bank",
        description = "REST APIs in Feather Bank to FETCH customer details"
)
@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private ICustomersService iCustomersService;

    public CustomerController(ICustomersService iCustomersService) {
        this.iCustomersService = iCustomersService;
    }

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch Customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails
            (
                    @RequestParam
                    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                    String mobileNumber
            ){
        CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.ok(customerDetailsDto);
    }
}
