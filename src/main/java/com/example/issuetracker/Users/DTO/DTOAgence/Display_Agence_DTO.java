package com.example.issuetracker.Users.DTO.DTOAgence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor@AllArgsConstructor
public class Display_Agence_DTO {
   private String name;
   private String idAgence;
   private String id_dsi;
   private String username_dsi;
   private Date createdAt;
}
