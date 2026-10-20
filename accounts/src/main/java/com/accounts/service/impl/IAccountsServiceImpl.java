package com.accounts.service.impl;

import com.accounts.constants.AccountsConstants;
import com.accounts.dto.AccountsDto;
import com.accounts.dto.CustomerDto;
import com.accounts.entity.Accounts;
import com.accounts.entity.Customer;
import com.accounts.exception.CustomerAlreadyExistsException;
import com.accounts.exception.ResourceNotFoundException;
import com.accounts.mapper.AccountsMapper;
import com.accounts.mapper.CustomerMapper;
import com.accounts.repositery.AccountsRepository;
import com.accounts.repositery.CustomerRepository;
import com.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class IAccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "+customerDto.getMobileNumber());
        }
        Customer saved = customerRepository.save(customer);
        accountsRepository.save(createNewAccounts(saved));
    }

    /**
     *
     * @param mobileNumber input mobile number
     * @return Accounts details based on a given number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
      Customer customer=customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Mobile number not found "+mobileNumber
                ));

      Accounts accounts= accountsRepository.findByCustomerId(customer.getCustomerId())
              .orElseThrow(()->new ResourceNotFoundException("Account not found with this customerId" +customer.getCustomerId().toString()));

      CustomerDto customerDto= CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
      customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
      return customerDto;
    }


    /**
     *
     * @param customerDto
     * @return
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto=customerDto.getAccountsDto();
        if (accountsDto!=null){
            Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(()->new ResourceNotFoundException("Account not found with this account number "
                    + accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accounts= accountsRepository.save(accounts);

            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId)
                    .orElseThrow(()->new ResourceNotFoundException("Customer not found with"+customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer not found with this mobleNumber"+mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


    /**
     *
     * @param customer
     * @return then new account
     */
    private Accounts createNewAccounts(Customer customer){
        Accounts newAccounts=new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber=1000000L + new Random().nextInt(900000000);

        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccounts;
    }
}
