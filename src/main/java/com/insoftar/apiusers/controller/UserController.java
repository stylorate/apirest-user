package com.insoftar.apiusers.controller;

import com.insoftar.apiusers.constants.Constants;
import com.insoftar.apiusers.controller.dto.ResponseDTO;
import com.insoftar.apiusers.controller.dto.UserDTO;
import com.insoftar.apiusers.controller.service.UserService;
import com.insoftar.apiusers.exception.EmailDuplicatedException;
import com.insoftar.apiusers.exception.MethodArgumentNotValidExceptionHandler;
import com.insoftar.apiusers.persistence.entity.User;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200"},
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE})
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    Logger log = (Logger) Logger.getLogger(UserController.class);

    @GetMapping()
    public ResponseEntity<ResponseDTO> getList() {
        ArrayList<UserDTO> list = service.getList();
        return (list.isEmpty())
                ? ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(
                new ResponseDTO().setMsg(Constants.NOT_FOUND))
                : ResponseEntity.status(HttpStatus.OK.value()).body(
                new ResponseDTO().setList(list).setMsg(HttpStatus.OK.getReasonPhrase()));
    }

    @GetMapping("/{idCard}")
    public ResponseEntity<ResponseDTO> getCity(@Valid @PathVariable String idCard) {
        Optional<User> temp = service.getUserByIdCard(idCard);
        return (temp.isPresent())
                ? ResponseEntity.status(HttpStatus.OK.value()).body(new ResponseDTO().setBody(modelMapper.map(
                temp.get(), UserDTO.class)).setMsg(HttpStatus.OK.getReasonPhrase()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(
                new ResponseDTO().setMsg(Constants.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody UserDTO request) {
        log.info("Llego al controller");
        try {
            if (!request.getIdCard().matches(Constants.REGEX_ONLY_NUMBERS)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                        new ResponseDTO().setMsg(Constants.VALID_ERROR)
                                .setFieldErrors(String.format(Constants.REGEX_ERROR_ONLY_NUMBERS, "IdCard"), "IdCard"));
            }
            if (!request.getTelephone().matches(Constants.REGEX_ONLY_NUMBERS)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
                        new ResponseDTO().setMsg(Constants.VALID_ERROR)
                                .setFieldErrors(String.format(
                                        Constants.REGEX_ERROR_ONLY_NUMBERS, "Telephone"), "Telephone"));
            }
            return ResponseEntity.status(HttpStatus.CREATED.value())
                    .body(new ResponseDTO().setBody(modelMapper.map(service.createOrUpdate(
                            modelMapper.map(request, User.class)), UserDTO.class)).setMsg(HttpStatus.OK.getReasonPhrase()));
        } catch (DataAccessException ex) {
            log.error("Error: " + ex.getMessage() + " " + ex.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDTO().setFieldErrors(ex.getMessage(), ex.getMostSpecificCause().getMessage())
                            .setMsg(Constants.PERSIST_ERROR));
        }
    }

    @DeleteMapping("/{idCard}")
    public ResponseEntity<ResponseDTO> delete(@Valid @PathVariable String idCard) {
        try{
            service.delete(idCard);
            return ResponseEntity.status(HttpStatus.OK.value()).body(
                    new ResponseDTO().setMsg(HttpStatus.OK.getReasonPhrase()));
        }catch (DataAccessException ex){
            log.error("Error: " + ex.getMessage() + " " + ex.getMostSpecificCause().getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO().setFieldErrors(ex.getMessage(), ex.getMostSpecificCause().getMessage())
                            .setMsg(Constants.NOT_FOUND));
        }
    }
}
