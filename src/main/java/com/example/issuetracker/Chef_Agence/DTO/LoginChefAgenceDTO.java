package com.example.issuetracker.Chef_Agence.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class LoginChefAgenceDTO {
    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
}
