package com.idevalot.accounts.mapper;

import com.idevalot.accounts.dto.AccountsDto;
import com.idevalot.accounts.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountsMapper {

    AccountsMapper MAPPER = Mappers.getMapper(AccountsMapper.class);

    AccountsDto mapToAccountsDto(Accounts account);

    Accounts mapToAccounts(AccountsDto accountsDto);

}
