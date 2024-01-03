package com.example.issuetracker.DSI.Controller;

import com.example.issuetracker.Users.DTOAgence.Creation_Agence_DTO;
import com.example.issuetracker.Users.DTOAgence.Display_Agence_DTO;
import com.example.issuetracker.Users.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.Entity.agence;
import com.example.issuetracker.Users.Service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dsi/agence")
public class AgenceController {
    @Autowired
    private AgenceService agenceService;

    @PostMapping("/add/{idDsi}")
    public ResponseEntity<Boolean> AddNewAgence(@PathVariable String idDsi, @RequestBody Creation_Agence_DTO creationAgenceDto) {
        System.out.println("Agence Name : "+creationAgenceDto.getAgenceName());
        return this.agenceService.addAgence(creationAgenceDto, idDsi);
    }

    @GetMapping("/list/{idDsi}")
    public ResponseEntity<List<Display_Agence_DTO>> ListAgenceById(@PathVariable String idDsi){
        return this.agenceService.ListAgenceByIdDsi(idDsi);
    }

    @PatchMapping("/update/{idDsi}/{idAgence}")
    public ResponseEntity<agence> UpdateAgence(@RequestBody Creation_Agence_DTO creationAgenceDto,@PathVariable String idDsi,@PathVariable String idAgence){
        System.out.println("idDsi : "+idDsi);
        System.out.println("IdAgence : "+idAgence);
        return this.agenceService.UpdateAgence(creationAgenceDto,idAgence,idDsi);
    }

    @DeleteMapping("/delet/{idDsi}/{idAgence}")
    public ResponseEntity<Boolean> DeleteAgence(@RequestBody PasswordDsi passwordDsi, @PathVariable String idDsi, @PathVariable String idAgence){
        System.out.println("idDsi : "+idDsi);
        System.out.println("IdAgence : "+idAgence);
        System.out.println("Password : "+passwordDsi.getPassword());
        return this.agenceService.DeletAgence(idAgence,idDsi,passwordDsi);
    }
    @GetMapping("/gebyid/{idDsi}/{idAgence}")
    public agence GetByID(@PathVariable String idDsi, @PathVariable String idAgence){
        System.out.println("idDsi : "+idDsi);
        System.out.println("IdAgence : "+idAgence);
        return this.agenceService.findAgenceByDSI(idDsi,idAgence);
    }
}
