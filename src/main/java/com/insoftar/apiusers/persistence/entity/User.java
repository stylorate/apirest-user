package com.insoftar.apiusers.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", updatable = false, nullable = false)
    private int idUser;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "LastName is mandatory")
    private String lastName;
    @NotBlank(message = "IdCard is mandatory")
    private String idCard;
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Telephone is mandatory")
    private String telephone;
}
