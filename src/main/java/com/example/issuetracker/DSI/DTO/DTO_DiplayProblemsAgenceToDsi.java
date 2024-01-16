package com.example.issuetracker.DSI.DTO;

import com.example.issuetracker.Users.Entity.Technicien;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class DTO_DiplayProblemsAgenceToDsi {
    private String id_Problem;
    private String Description;
    private String TypeUser;
    private String UserName;
    private String AgenceName;
    private StatusProblem statusProblem;
    private Technicien technicien;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Date createdAt;
}
