package com.example.issuetracker.Users.DTO.DTOEmployer;

import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class EmployerRequest {
    private CreationEmployerDTO creationEmployerDTO;
    private PasswordDsi passwordDsi;
}
