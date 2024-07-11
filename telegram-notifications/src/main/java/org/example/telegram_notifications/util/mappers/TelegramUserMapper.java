package org.example.telegram_notifications.util.mappers;

import org.example.telegram_notifications.dto.TelegramUserDTO;
import org.example.telegram_notifications.persistance.entity.TelegramUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TelegramUserMapper {

    TelegramUserMapper INSTANCE = Mappers.getMapper(TelegramUserMapper.class);

    TelegramUserDTO userToUserDto(TelegramUser user);
}
