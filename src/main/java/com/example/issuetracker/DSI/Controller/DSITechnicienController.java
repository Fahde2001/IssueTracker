package com.example.issuetracker.DSI.Controller;

import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTO.DTOTechnicien.TechnicienDisplyDTO;
import com.example.issuetracker.Users.DTO.DTOTechnicien.TechnicineRequest;
import com.example.issuetracker.Users.Service.DSITechnicienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dsi/technicien")
public class DSITechnicienController {
    @Autowired
    private DSITechnicienService technicienService;


    @PostMapping("/add/{IdDsi}")
    public ResponseEntity<Boolean> AddTechnicien(
            @RequestBody TechnicineRequest request,
            @PathVariable String IdDsi) {
        System.out.println("IdDSI : "+IdDsi);
        System.out.println("Password : "+request.getPasswordDsi().getPassword());
        System.out.println("Username : "+request.getCreationTechnicienDTO().getUserName());
        System.out.println("Password : "+request.getCreationTechnicienDTO().getPassword());
        System.out.println("Category : "+request.getCreationTechnicienDTO().getCategory());
        return this.technicienService.AddUserTechnicien(IdDsi, request.getPasswordDsi(), request.getCreationTechnicienDTO());
    }

    @GetMapping("/listAll/{IdDsi}")
    public ResponseEntity<List<TechnicienDisplyDTO>> getAllTechnicien(@PathVariable String IdDsi){
        return this.technicienService.GetAllEmployer(IdDsi);
    }
    @DeleteMapping("/delete/{IdDsi}/{IdTechnicien}")
    public ResponseEntity<Boolean> DeleteTechnicien(@PathVariable String IdDsi,@PathVariable String IdTechnicien,@RequestBody PasswordDsi passwordDsi){
        return  this.technicienService.DeleteTechnicien(IdDsi,IdTechnicien,passwordDsi);
    }
    @PatchMapping("/update/{IdDsi}/{IdTechnicien}")
    public ResponseEntity<Boolean> UpdateEmployer(@PathVariable String IdDsi,@PathVariable String IdTechnicien,@RequestBody TechnicineRequest request)
    {
        return this.technicienService.UpdateTechnicien(IdDsi,IdTechnicien,request.getPasswordDsi(), request.getCreationTechnicienDTO());
    }

}
