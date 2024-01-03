package com.example.issuetracker.Users.DTOAgence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data@NoArgsConstructor@AllArgsConstructor@ToString
public class PasswordDsi {
    @JsonProperty("Password")
    private String Password;


}
