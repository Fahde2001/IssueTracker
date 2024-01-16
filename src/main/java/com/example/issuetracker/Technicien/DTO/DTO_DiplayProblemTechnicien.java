package com.example.issuetracker.Technicien.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class DTO_DiplayProblemTechnicien {
    private String id_Problem;
    private String description;
    private String statusProblem;
    private String AgenceName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date createdAt;
}
