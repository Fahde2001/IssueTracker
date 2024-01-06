package com.example.issuetracker.Users.DTO.DTOTechnicien;

import com.example.issuetracker.Users.Entity.Category;
import com.example.issuetracker.Users.Entity.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@NoArgsConstructor@AllArgsConstructor
public class TechnicienDisplyDTO {
    private String IdTechnicien;
    private String UserName;
    private String IdDsi;
    private Category category;
    private TypeUser typeUser;
    private Date CreateAt;
}
