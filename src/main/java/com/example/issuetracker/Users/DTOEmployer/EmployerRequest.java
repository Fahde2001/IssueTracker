package com.example.issuetracker.Users.DTOEmployer;

import com.example.issuetracker.Users.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTOChefAgence.CretionChefAgenceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class EmployerRequest {
    private CreationEmployerDTO creationEmployerDTO;
    private PasswordDsi passwordDsi;
}
