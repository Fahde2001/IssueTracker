package com.example.issuetracker.Employer.Service;

import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.ChefAgence;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Repository.EmployerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class EmployerService {
    @Autowired
    private EmployerRepository employerRepository;

    public ResponseEntity<Employer> LoginEmployer(DTOLogin loginDTO){
        try {
            ValidInputLogin(loginDTO.getUserName(),loginDTO.getPassword());
            Employer employer=GetEmployeByUserName(loginDTO.getUserName());
            boolean status=CheckPasswordUser(employer.getUserNameEmployer(),loginDTO.getPassword());
            System.out.println("\n\n\n status loginfunction : \t"+status);
            if (status){
                return new ResponseEntity<>(employer, HttpStatus.OK);
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
    private Employer GetEmployeByUserName(String UserName)
    {
        Optional<Employer> optionalEmployer=this.employerRepository.findEmployerBy_UserName(UserName);
        return optionalEmployer.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"employer Not Found"));
    }
    private Boolean CheckPasswordUser(String UserName,String  Password){
        Employer employer=GetEmployeByUserName(UserName);
        boolean status = BCrypt.checkpw(Password,employer.getPasswrodEmployer());
        System.out.println("\n\n\n status checkpassword : \t"+status);
        if (status){
            return true;
        }else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your Password is not Valide");
        }
    }

}
