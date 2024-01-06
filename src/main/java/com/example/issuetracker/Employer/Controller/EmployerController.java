package com.example.issuetracker.Employer.Controller;

import com.example.issuetracker.Chef_Agence.Service.ChefAgenceService;
import com.example.issuetracker.Employer.Service.EmployerService;
import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.ChefAgence;
import com.example.issuetracker.Users.Entity.Employer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    @PostMapping("/login")
    public ResponseEntity<Employer> LoginChefAgence(@RequestBody DTOLogin loginDTO){
        System.out.println("\n\n\n\n\npassword :\t"+loginDTO.getPassword());
        return this.employerService.LoginEmployer(loginDTO);
    }
}