package com.example.issuetracker.Technicien.Service;

import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.Technicien;
import com.example.issuetracker.Users.Repository.TechnicienRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class TechnicienService {
    @Autowired
    private TechnicienRepository technicienRepository;


    public ResponseEntity<Technicien> LoginTechnicien(DTOLogin loginDTO){
        try {
            ValidInputLogin(loginDTO.getUserName(),loginDTO.getPassword());
            Technicien technicien=GetTechnicienByUserName(loginDTO.getUserName());
            boolean status=CheckPasswordUser(technicien.getUserNameTechnicien(),loginDTO.getPassword());
            System.out.println("\n\n\n status loginfunction : \t"+status);
            if (status){
                return new ResponseEntity<>(technicien, HttpStatus.OK);
            }
            else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"your password or username is not correct");
            }
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    private void ValidInputLogin(String UserName,String Password){
        if(UserName == null || Password == null)throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Your data is null");
    }
    private Technicien GetTechnicienByUserName(String UserName)
    {
        Optional<Technicien> optionalTechnicien=this.technicienRepository.findTechnicienBy_UserName(UserName);
        return optionalTechnicien.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"Technicien Not Found"));
    }
    private Boolean CheckPasswordUser(String UserName,String  Password){
        Technicien technicien=GetTechnicienByUserName(UserName);
        boolean status = BCrypt.checkpw(Password,technicien.getPasswordTechnicien());
        System.out.println("\n\n\n status checkpassword : \t"+status);
        if (status){
            return true;
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your Password is not Valide");
        }
    }

}
