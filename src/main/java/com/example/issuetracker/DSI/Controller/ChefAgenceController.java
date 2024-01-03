package com.example.issuetracker.DSI.Controller;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.Users.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTOChefAgence.ChefAgenceDisplyDTO;
import com.example.issuetracker.Users.DTOChefAgence.ChefAgenceRequest;
import com.example.issuetracker.Users.DTOChefAgence.CretionChefAgenceDTO;
import com.example.issuetracker.Users.Entity.agence;
import com.example.issuetracker.Users.Entity.chefAgence;
import com.example.issuetracker.Users.Function.FunctionAgence;
import com.example.issuetracker.Users.Function.FunctionDSI;
import com.example.issuetracker.Users.Service.ChefAgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dsi/ChefAgence")
public class ChefAgenceController {
    @Autowired
    private ChefAgenceService chefAgenceService;
    @Autowired
    private FunctionDSI functionDSI;
    @Autowired
    private FunctionAgence functionAgence;

    @PostMapping("/add/{IdDsi}/{IdAgence}")
    public ResponseEntity<Boolean> addNewChefAgence(
            @RequestBody ChefAgenceRequest request,
            @PathVariable String IdDsi,
            @PathVariable String IdAgence) {
        System.out.println("IdDSI : "+IdDsi);
        System.out.println("IdAgence : "+IdAgence);
        System.out.println("Password : "+request.getPasswordDsi().getPassword());
        System.out.println("Username : "+request.getCretionChefAgenceDTO().getUserName());
        System.out.println("Password : "+request.getCretionChefAgenceDTO().getPassword());
        return chefAgenceService.ADDUserChefAgence(IdDsi, IdAgence, request.getPasswordDsi(), request.getCretionChefAgenceDTO());
    }

    @GetMapping("/listAll/{IdDsi}")
    public ResponseEntity<List<ChefAgenceDisplyDTO>> getAllChefAgence(@PathVariable String IdDsi){
        return this.chefAgenceService.GetAllChefAgence(IdDsi);
    }
    @DeleteMapping("/delete/{IdDsi}/{IdchefAgence}")
    public ResponseEntity<Boolean> DeleteChefAgence(@PathVariable String IdDsi,@PathVariable String IdchefAgence,@RequestBody PasswordDsi passwordDsi){
        return  this.chefAgenceService.DeleteChefAgence(IdDsi,IdchefAgence,passwordDsi);
    }
    @PatchMapping("/update/{IdDsi}/{IdChefAgence}")
    public ResponseEntity<Boolean> UpdateChefAgence(@PathVariable String IdDsi,@PathVariable String IdChefAgence,@RequestBody ChefAgenceRequest request)
    {
        return this.chefAgenceService.UpdateChefAgence(IdDsi,IdChefAgence,request.getPasswordDsi(), request.getCretionChefAgenceDTO());
    }


}
