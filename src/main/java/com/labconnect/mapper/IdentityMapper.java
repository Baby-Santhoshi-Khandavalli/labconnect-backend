package com.labconnect.mapper;

import com.labconnect.DTORequest.UserRequestDTO;
import com.labconnect.DTOResponse.AuditLogDTO;
import com.labconnect.DTOResponse.UserResponseDTO;
import com.labconnect.models.AuditLog;
import com.labconnect.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdentityMapper {
    UserResponseDTO mapToUserResponseDTO(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target="auditLogs", ignore=true)
    @Mapping(target = "password", ignore = true)
    User mapToUserEntity(UserRequestDTO userRequestDTO);

    AuditLogDTO mapToAuditLogDTO(AuditLog auditLog);
}
