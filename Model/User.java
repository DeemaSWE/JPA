package com.example.ecommerce.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 5, message = "Username must be at least 5 characters")
    @Column(columnDefinition = "varchar(20) not null check (length(username) >= 5)")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Password must be at least 6 characters long and contain both letters and digits")
    @Column(columnDefinition = "varchar(50) not null")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid email format")
    @Column(columnDefinition = "varchar(50) not null")
    private String email;


    @NotEmpty(message = "Role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either Admin or Customer")
    @Column(columnDefinition = "varchar(20) not null check (role = 'Admin' or role = 'Customer')")
    private String role;

    @NotNull(message = "Balance cannot be empty")
    @Positive(message = "Balance must be a positive number")
    @Column(columnDefinition = "double not null")
    private Double balance;

}
