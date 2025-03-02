package com.idevalot.accounts.service.impl;

import com.idevalot.accounts.constants.AccountsConstants;
import com.idevalot.accounts.dto.AccountsDto;
import com.idevalot.accounts.dto.CustomerDto;
import com.idevalot.accounts.entity.Accounts;
import com.idevalot.accounts.entity.Customer;
import com.idevalot.accounts.exception.CustomerAlreadyExistsException;
import com.idevalot.accounts.exception.ResourceNotFoundException;
import com.idevalot.accounts.mapper.AccountsMapper;
import com.idevalot.accounts.mapper.CustomerMapper;
import com.idevalot.accounts.repository.AccountRepository;
import com.idevalot.accounts.repository.CustomerRepository;
import com.idevalot.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.MAPPER.mapToCustomer(customerDto);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number "+customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    private  Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 100000000L + new Random().nextInt(900000000);

        accounts.setAccountNumber(randomAccNumber);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        return accounts;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.MAPPER.mapToCustomerDto(customer);
        customerDto.setAccountsDto(AccountsMapper.MAPPER.mapToAccountsDto(accounts));

        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            Accounts newAccount = AccountsMapper.MAPPER.mapToAccounts(accountsDto);
            newAccount.setCustomerId(accounts.getCustomerId());
            accounts = accountRepository.save(newAccount);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            Customer newCustomer = CustomerMapper.MAPPER.mapToCustomer(customerDto);
            newCustomer.setCustomerId(customerId);
            customerRepository.save(newCustomer);

            isUpdated = true;

        }

        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
