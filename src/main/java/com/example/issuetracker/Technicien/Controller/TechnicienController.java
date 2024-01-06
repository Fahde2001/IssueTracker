package com.example.issuetracker.Technicien.Controller;

import com.example.issuetracker.Employer.Service.EmployerService;
import com.example.issuetracker.Technicien.Service.TechnicienService;
import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.Technicien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/technicien")
public class TechnicienController {

    @Autowired
    private TechnicienService technicienService;
    @PostMapping("/login")
    public ResponseEntity<Technicien> LoginTechnicien(@RequestBody DTOLogin loginDTO){
        System.out.println("\n\n\n\n\npassword :\t"+loginDTO.getPassword());
        return this.technicienService.LoginTechnicien(loginDTO);
    }
}
