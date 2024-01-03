package com.example.issuetracker.Users.DTOChefAgence;

import com.example.issuetracker.Users.DTOAgence.PasswordDsi;
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
