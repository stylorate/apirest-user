package com.insoftar.apiusers.config;

import com.insoftar.apiusers.controller.dto.UserDTO;
import com.insoftar.apiusers.persistence.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(UserDTO.class, User.class);
        modelMapper.addMappings(new PropertyMap<User, User>() {
            @Override
            protected void configure() {
                skip(destination.getIdUser());
            }
        });

        modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
