package com.example.issuetracker.Users.DTO.DTOChefAgence;

import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChefAgenceRequest {
    private CretionChefAgenceDTO cretionChefAgenceDTO;
    private PasswordDsi passwordDsi;
}
