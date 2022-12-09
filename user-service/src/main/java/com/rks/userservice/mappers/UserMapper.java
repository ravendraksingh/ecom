package com.rks.userservice.mappers;

import com.rks.userservice.domains.User;
import com.rks.userservice.dto.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends ModelMapper {

    @Override
    public <User, UserResponse> TypeMap<User, UserResponse> addMappings(PropertyMap<User, UserResponse> propertyMap) {
        return super.addMappings(propertyMap);
    }
}
