package com.example.issuetracker.Users.DTO.DTOTechnicien;

import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class TechnicineRequest {
    private CreationTechnicienDTO creationTechnicienDTO;
    private PasswordDsi passwordDsi;
}
