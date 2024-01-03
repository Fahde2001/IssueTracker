package com.example.issuetracker.Users.DTOEmployer;

import com.example.issuetracker.Users.Entity.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@NoArgsConstructor@AllArgsConstructor
public class EmployerDisplyDTO {
    private String IdEmployer;
    private String UserName;
    private String IdDsi;
    private  String IdAgence;
    private String NameAgence;
    private TypeUser typeUser;
    private Date CreateAt;
}
