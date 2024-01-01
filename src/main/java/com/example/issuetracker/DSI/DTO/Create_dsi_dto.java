package com.example.issuetracker.DSI.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Create_dsi_dto {

    @JsonProperty("UserName")
    private String UserName;
    @JsonProperty("Password")
    private String Password;
    @JsonProperty("Name_Company")
    private String Name_Company;
}
