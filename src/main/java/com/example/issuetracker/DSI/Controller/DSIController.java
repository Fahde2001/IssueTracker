package com.example.issuetracker.DSI.Controller;

import com.example.issuetracker.DSI.DTO.Create_dsi_dto;
import com.example.issuetracker.DSI.DTO.DTO_DiplayProblemsAgenceToDsi;
import com.example.issuetracker.DSI.DTO.DTO_ProblemDSI;
import com.example.issuetracker.DSI.DTO.login_Dsi_DTO;
import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Service.DSISerivice;
import com.example.issuetracker.DSI.Service.ProblemDSI;
import com.example.issuetracker.problem.DTO.DtoCreateProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dsi")
public class DSIController {
    @Autowired
    private DSISerivice  dsiSerivice;
    @Autowired
    private ProblemDSI problemDSI;

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

    @PostMapping("/problem/{id_dsi}")
    public ResponseEntity<Boolean> AddProblem(@PathVariable String id_dsi, @RequestBody DtoCreateProblem dtoCreateProblem){
        System.out.println("\n\n\n id  Employer \t"+id_dsi+"\n");
        System.out.println("dto"+dtoCreateProblem.getDescription()+"\n\n");
        return this.problemDSI.AddProblemDSI(id_dsi,dtoCreateProblem);
    }
    @GetMapping("/problem/list/{id_dsi}")
    public ResponseEntity<List<DTO_ProblemDSI>> GetALlProblemsDSI(@PathVariable String id_dsi){
        System.out.println("\n\n\n id  Employer \t"+id_dsi+"\n");
        return this.problemDSI.DisplayProblemDSI(id_dsi);
    }
    @GetMapping("/problem/list/agence/{id_dsi}")
    public ResponseEntity<List<DTO_DiplayProblemsAgenceToDsi>> GetAllProblemAgenceToDSIById(@PathVariable String id_dsi){
        System.out.println("\n\n\n id  Employer \t"+id_dsi+"\n");
        return this.problemDSI.GetAllProblemsAgenceToDSI(id_dsi);
    }
    @PostMapping("/problem/Assignments/{id_DSI}/{id_Technecien}/{id_Problem}")
    public ResponseEntity<Boolean> AssignmentsProblem(@PathVariable @NonNull String id_DSI, @PathVariable @NonNull String id_Technecien, @PathVariable @NonNull String id_Problem){
        System.out.println("Hello world!");
        return this.problemDSI.AssignmentsProblem(id_DSI,id_Problem,id_Technecien);
    }

}
