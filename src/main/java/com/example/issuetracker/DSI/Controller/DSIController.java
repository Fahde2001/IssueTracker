package com.example.issuetracker.DSI.Controller;

import com.example.issuetracker.DSI.DTO.Create_dsi_dto;
import com.example.issuetracker.DSI.DTO.login_Dsi_DTO;
import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Service.DSISerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/dsi")
public class DSIController {
    @Autowired
    private DSISerivice  dsiSerivice;

    @GetMapping()
    public String test(){
        return "Hello";
    }

    @PostMapping("/Create")
    public ResponseEntity<Boolean> addNewDsi(@RequestBody Create_dsi_dto createDsiDto){
        System.out.println("CONTROLLER \n username : "+createDsiDto.getUserName()+" password : "+createDsiDto.getPassword()+" name company : "+createDsiDto.getName_Company());
        if(createDsiDto.getUserName()==null || createDsiDto.getPassword() == null || createDsiDto.getName_Company() == null){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"required to remplier all field");
        }
        return this.dsiSerivice.AddUserDsi(createDsiDto);
    }
    @PostMapping("/login")
    public ResponseEntity<DSI> loginDsi(@RequestBody login_Dsi_DTO loginDsiDto){
        return  this.dsiSerivice.loginDsi(loginDsiDto);
    }



}
