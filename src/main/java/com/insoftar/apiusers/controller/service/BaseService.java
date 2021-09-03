package com.insoftar.apiusers.controller.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseService {

    @Autowired
    ModelMapper modelMapper;

    public <S, T> List<T> mapList(ArrayList<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
