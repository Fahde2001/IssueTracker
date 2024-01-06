package com.example.issuetracker.Users.DTO.DTOChefAgence;

import com.example.issuetracker.Users.Entity.TypeUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data@NoArgsConstructor@AllArgsConstructor
public class ChefAgenceDisplyDTO {
    private String IdChefAgence;
    private String UserName;
    private String IdDsi;
    private  String IdAgence;
    private String NameAgence;
    private TypeUser typeUser;
    private Date CreateAt;

}
