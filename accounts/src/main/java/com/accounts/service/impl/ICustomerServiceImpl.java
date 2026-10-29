package com.accounts.service.impl;

import com.accounts.dto.AccountsDto;
import com.accounts.dto.CardsDto;
import com.accounts.dto.CustomerDetailsDto;
import com.accounts.dto.LoansDto;
import com.accounts.entity.Accounts;
import com.accounts.entity.Customer;
import com.accounts.exception.ResourceNotFoundException;
import com.accounts.mapper.AccountsMapper;
import com.accounts.mapper.CustomerMapper;
import com.accounts.repositery.AccountsRepository;
import com.accounts.repositery.CustomerRepository;
import com.accounts.service.ICustomerService;
import com.accounts.service.client.CardsClient;
import com.accounts.service.client.LoansClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ICustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsClient cardsClient;
    private final LoansClient loansClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException("Customer with this mobile number not found "+mobileNumber));

        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(()->new ResourceNotFoundException("Accoutns not found"));

        CustomerDetailsDto customerDetailsDto= CustomerMapper.mapToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity=loansClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity=cardsClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
