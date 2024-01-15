package com.example.issuetracker.Chef_Agence.Controller;

import com.example.issuetracker.Chef_Agence.Service.ChefAgenceService;
import com.example.issuetracker.Chef_Agence.Service.ProblemChefAgence;
import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.ChefAgence;
import com.example.issuetracker.problem.DTO.DtoCreateProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chefAgence")
public class ChefAgenceController {

    @Autowired
    private ChefAgenceService chefAgenceService;
    @Autowired
    private ProblemChefAgence problemChefAgence;
    @PostMapping("/login")
    public ResponseEntity<ChefAgence> LoginChefAgence(@RequestBody DTOLogin loginChefAgenceDTO){
        System.out.println("\n\n\n\n\npassword :\t"+loginChefAgenceDTO.getPassword());
        return this.chefAgenceService.LoginChefgence(loginChefAgenceDTO);
    }

    @PostMapping("/problem/{id_chefAgence}")
    public ResponseEntity<Boolean> AddProblem(@PathVariable String id_chefAgence, @RequestBody DtoCreateProblem dtoCreateProblem){
        System.out.println("\n\n\n id  Employer \t"+id_chefAgence+"\n");
        System.out.println("dto"+dtoCreateProblem.getDescription()+"\n\n");
        return this.problemChefAgence.AddProblemChefAgecne(id_chefAgence,dtoCreateProblem);
    }
}
