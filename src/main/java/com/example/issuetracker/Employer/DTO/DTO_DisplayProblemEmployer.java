package com.example.issuetracker.Employer.DTO;

import com.example.issuetracker.problem.Entity.ProblemCategory;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@NoArgsConstructor@AllArgsConstructor
public class DTO_DisplayProblemEmployer {
        private String description;

        private ProblemCategory problemCategory;

        private StatusProblem statusProblem;
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
        private Date createdAt;

}
