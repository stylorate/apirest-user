package com.insoftar.apiusers.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@ToString
@ConstructorBinding
public class UserDTO {

    private int idUser;
    @NotEmpty
    @Size(min = 3, max = 10)
    private String name;
    @NotEmpty
    @Size(min = 3, max = 10)
    private String lastName;
    @NotEmpty
    @Size(min = 8, max = 10)
    private String idCard;
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty
    @Size(min = 7, max = 10)
    private String telephone;
}
