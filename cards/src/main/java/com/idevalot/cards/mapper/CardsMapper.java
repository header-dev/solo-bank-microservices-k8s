package com.idevalot.cards.mapper;

import com.idevalot.cards.dto.CardsDto;
import com.idevalot.cards.entity.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardsMapper {

    CardsMapper MAPPER = Mappers.getMapper(CardsMapper.class);

    CardsDto mapToCardsDto(Cards cards);

    Cards mapToCards(CardsDto cardsDto);
}
