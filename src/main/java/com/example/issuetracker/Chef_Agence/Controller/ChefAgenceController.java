package com.example.issuetracker.Chef_Agence.Controller;

import com.example.issuetracker.Chef_Agence.DTO.LoginChefAgenceDTO;
import com.example.issuetracker.Chef_Agence.Service.ChefAgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chefAgence")
public class ChefAgenceController {

    @Autowired
    private ChefAgenceService chefAgenceService;
    @PostMapping("/login")
    public ResponseEntity<Boolean> LoginChefAgence(@RequestBody LoginChefAgenceDTO loginChefAgenceDTO){
        System.out.println("\n\n\n\n\npassword :\t"+loginChefAgenceDTO.getPassword());
        return this.chefAgenceService.LoginChefgence(loginChefAgenceDTO);
    }
}
