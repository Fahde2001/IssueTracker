package com.example.issuetracker.problem.DTO;

import com.example.issuetracker.problem.Entity.ProblemCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.net.Proxy;

@Data@NoArgsConstructor@AllArgsConstructor
public class DtoCreateProblem {
    @JsonProperty("description")
    @NonNull
    private String description;

    @JsonProperty("problemCategory")
    @Enumerated(EnumType.STRING)
    @NonNull
    private ProblemCategory problemCategory;
}
