package com.insoftar.apiusers.controller.service;

import com.insoftar.apiusers.constants.Constants;
import com.insoftar.apiusers.controller.dto.UserDTO;
import com.insoftar.apiusers.exception.EmailDuplicatedException;
import com.insoftar.apiusers.persistence.entity.User;
import com.insoftar.apiusers.persistence.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService extends BaseService {
    @Autowired
    UserRepository repository;

    Logger log = (Logger) Logger.getLogger(UserService.class);

    @Transactional(readOnly = true)
    public ArrayList<UserDTO> getList() {
        ArrayList<User> temp = (ArrayList<User>) repository.findAll();
        return (ArrayList<UserDTO>) mapList(temp, UserDTO.class);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByIdCard(String id) {
        return repository.findByIdCard(id);
    }

    public User createOrUpdate(User user) {
        log.info("Llego al service");
        Optional<User> optionalUser = repository.findByIdCard(user.getIdCard());

        if (optionalUser.isPresent()) {
            modelMapper.map(user, optionalUser.get());
            return repository.save(optionalUser.get());
        }
        return repository.save(user);
    }

    public void delete(String idCard) {
        repository.deleteById(Integer.parseInt(idCard));
    }

    public boolean validateRegex(String field, String regex) {
        Pattern pat = Pattern.compile(regex);
        Matcher matcher = pat.matcher(field);
        return matcher.find();
    }


}
