package com.idevalot.accounts.mapper;

import com.idevalot.accounts.dto.CustomerDto;
import com.idevalot.accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerDto mapToCustomerDto(Customer customer);

    Customer mapToCustomer(CustomerDto customerDto);

}
