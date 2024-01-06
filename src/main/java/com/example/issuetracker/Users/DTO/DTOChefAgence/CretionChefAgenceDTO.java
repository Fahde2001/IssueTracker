package com.example.issuetracker.Users.DTO.DTOChefAgence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data@NoArgsConstructor@AllArgsConstructor
public class CretionChefAgenceDTO {
    @JsonProperty("UserNAme")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
}
