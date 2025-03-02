package com.idevalot.loans.mapper;

import com.idevalot.loans.dto.LoansDto;
import com.idevalot.loans.entity.Loans;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoansMapper {

    LoansMapper MAPPER = Mappers.getMapper(LoansMapper.class);

    LoansDto mapToLoansDto(Loans loans);

    Loans mapToLoans(LoansDto loansDto);

}
