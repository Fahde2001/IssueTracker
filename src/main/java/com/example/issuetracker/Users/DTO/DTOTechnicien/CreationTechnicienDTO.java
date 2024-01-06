package com.example.issuetracker.Users.DTO.DTOTechnicien;

import com.example.issuetracker.Users.Entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor@NoArgsConstructor@Data
public class CreationTechnicienDTO {
    @JsonProperty("UserNAme")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
    @JsonProperty("Category")
    private Category category;
}
