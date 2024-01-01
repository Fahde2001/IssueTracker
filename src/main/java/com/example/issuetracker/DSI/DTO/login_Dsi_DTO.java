package com.example.issuetracker.DSI.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class login_Dsi_DTO {
    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
}
