package org.example.emergency.util.mappers;

import org.example.emergency.dto.response.EmergencyCallDTO;
import org.example.emergency.entity.EmergencyCall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmergencyCallMapper {

    EmergencyCallMapper INSTANCE = Mappers.getMapper(EmergencyCallMapper.class);

    @Mapping(target = "receiverId", expression = "java(emergencyCall.getReceiver().getId())")
    EmergencyCallDTO callToCallDto(EmergencyCall emergencyCall);
}
