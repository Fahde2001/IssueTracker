package com.example.issuetracker.Users.DTO.DTOAgence;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor
public class Creation_Agence_DTO {
    @JsonProperty("AgenceName")
    private String AgenceName;
}
