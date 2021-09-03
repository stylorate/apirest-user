package com.insoftar.apiusers.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.insoftar.apiusers.exception.MethodArgumentNotValidExceptionHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    private String message;

    private Object body;

    private ArrayList<T> list;

    //    private Error error;
    private ArrayList<FieldErrorHandler> fieldErrors;

    public ResponseDTO setMsg(String msg) {
        this.message = msg;
        return this;
    }

    public ResponseDTO setBody(Object o) {
        this.body = o;
        return this;
    }

    public ResponseDTO setList(ArrayList<T> listObject) {
        list = listObject;
        return this;
    }

    public ResponseDTO setFieldErrors(String message, String field) {
        this.fieldErrors = new ArrayList<>();
        this.fieldErrors.add(new FieldErrorHandler(field, message));
        return this;
    }



    @Getter
    @Setter
    static class FieldErrorHandler {
        private String field;
        private String message;

        public FieldErrorHandler(String field, String message) {
            this.message = message;
            this.field = field;
        }
    }
}
