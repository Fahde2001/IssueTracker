package com.example.issuetracker.Employer.Controller;

import com.example.issuetracker.Employer.Service.LogInEmployerService;
import com.example.issuetracker.Employer.Service.ProblemEmployerService;
import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.problem.DTO.DtoCreateProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private LogInEmployerService employerService;
    @Autowired
    private ProblemEmployerService problemEmployerService;
    @PostMapping("/login")
    public ResponseEntity<Employer> LoginChefAgence(@RequestBody DTOLogin loginDTO){
        System.out.println("\n\n\n\n\npassword :\t"+loginDTO.getPassword());
        return this.employerService.LoginEmployer(loginDTO);
    }

    @PostMapping("/problem/{id_employer}")
    public ResponseEntity<Boolean> AddProblem(@PathVariable String id_employer, @RequestBody DtoCreateProblem dtoCreateProblem){
        System.out.println("\n\n\n id  Employer \t"+id_employer+"\n");
        System.out.println("dto"+dtoCreateProblem.getDescription()+"\n\n");
        return this.problemEmployerService.AddProblemEmployer(id_employer,dtoCreateProblem);
    }
}