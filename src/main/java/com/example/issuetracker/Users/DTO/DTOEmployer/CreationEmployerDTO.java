package com.example.issuetracker.Users.DTO.DTOEmployer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class CreationEmployerDTO {
    @JsonProperty("UserNAme")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
}
