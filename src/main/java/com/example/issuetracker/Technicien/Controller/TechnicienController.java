package com.example.issuetracker.Technicien.Controller;

import com.example.issuetracker.Technicien.DTO.DTO_DiplayProblemTechnicien;
import com.example.issuetracker.Technicien.DTO.DTO_UpdateStatus;
import com.example.issuetracker.Technicien.Service.ProblemServiceTechnicien;
import com.example.issuetracker.Technicien.Service.TechnicienService;
import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.Technicien;
import com.example.issuetracker.problem.Entity.Problem;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/technicien")
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;

    @Autowired
    private ProblemServiceTechnicien problemServiceTechnicien;

    @PostMapping("/login")
    public ResponseEntity<Technicien> LoginTechnicien(@RequestBody DTOLogin loginDTO){
        System.out.println("\n\n\n\n\npassword :\t"+loginDTO.getPassword());
        return this.technicienService.LoginTechnicien(loginDTO);
    }

    @GetMapping("/problems/assigments/{id_technicien}")
    public ResponseEntity<List<DTO_DiplayProblemTechnicien>> GetAllProblemsAssigments(@PathVariable @NonNull String id_technicien){
        return this.problemServiceTechnicien.GetProblemAssigmentTechnicien(id_technicien);
    }
    @PostMapping("/problems/status/{id_technicien}/{id_Problem}")
    public ResponseEntity<Problem> UpdateStatusProblem(
            @PathVariable @NonNull String id_technicien,
            @PathVariable String id_Problem,
            @RequestBody DTO_UpdateStatus dtoUpdateStatus) {

        return this.problemServiceTechnicien.UpdateStatusProblem(id_technicien, id_Problem, dtoUpdateStatus);
    }
}
