package com.example.issuetracker.Chef_Agence.DTO;

import com.example.issuetracker.problem.Entity.ProblemCategory;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@NoArgsConstructor@AllArgsConstructor
public class DTO_DisplayChefAgence {
    private String description;

    private ProblemCategory problemCategory;

    private StatusProblem statusProblem;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date createdAt;
}
