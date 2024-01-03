package com.example.issuetracker.DSI.Controller;

import com.example.issuetracker.Users.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTOChefAgence.ChefAgenceDisplyDTO;
import com.example.issuetracker.Users.DTOChefAgence.ChefAgenceRequest;
import com.example.issuetracker.Users.DTOEmployer.EmployerDisplyDTO;
import com.example.issuetracker.Users.DTOEmployer.EmployerRequest;
import com.example.issuetracker.Users.Service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dsi/employer")
public class EmployerController {
    @Autowired
    private EmployeService employeService;

    @PostMapping("/add/{IdDsi}/{IdAgence}")
    public ResponseEntity<Boolean> AddEmployer(
            @RequestBody EmployerRequest request,
            @PathVariable String IdDsi,
            @PathVariable String IdAgence) {
        System.out.println("IdDSI : "+IdDsi);
        System.out.println("IdAgence : "+IdAgence);
        System.out.println("Password : "+request.getPasswordDsi().getPassword());
        System.out.println("Username : "+request.getCreationEmployerDTO().getUserName());
        System.out.println("Password : "+request.getCreationEmployerDTO().getPassword());
        return this.employeService.AddUserEmployer(IdDsi, IdAgence, request.getPasswordDsi(), request.getCreationEmployerDTO());
    }

    @GetMapping("/listAll/{IdDsi}")
    public ResponseEntity<List<EmployerDisplyDTO>> getAllEmployer(@PathVariable String IdDsi){
        return this.employeService.GetAllEmployer(IdDsi);
    }
    @DeleteMapping("/delete/{IdDsi}/{IdEmployer}")
    public ResponseEntity<Boolean> DeleteEmployer(@PathVariable String IdDsi,@PathVariable String IdEmployer,@RequestBody PasswordDsi passwordDsi){
        return  this.employeService.DeleteEmployer(IdDsi,IdEmployer,passwordDsi);
    }
    @PatchMapping("/update/{IdDsi}/{IdEmployer}")
    public ResponseEntity<Boolean> UpdateEmployer(@PathVariable String IdDsi,@PathVariable String IdEmployer,@RequestBody EmployerRequest request)
    {
        return this.employeService.UpdateEmployer(IdDsi,IdEmployer,request.getPasswordDsi(), request.getCreationEmployerDTO());
    }


}
