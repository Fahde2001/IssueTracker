package com.example.issuetracker.Users.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class DTOLogin {
    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
}
