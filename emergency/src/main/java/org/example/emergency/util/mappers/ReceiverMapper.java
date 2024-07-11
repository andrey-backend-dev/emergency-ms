package org.example.emergency.util.mappers;

import org.example.emergency.dto.response.ReceiverDTO;
import org.example.emergency.entity.Receiver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReceiverMapper {

    @Mapping(target = "callerId", expression = "java(receiver.getCaller().getId())")
    public ReceiverDTO receiverToReceiverDto(Receiver receiver);
}
