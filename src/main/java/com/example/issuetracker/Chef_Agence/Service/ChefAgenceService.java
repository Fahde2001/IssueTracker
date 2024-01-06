package com.example.issuetracker.Chef_Agence.Service;

import com.example.issuetracker.Users.DTO.DTOLogin;
import com.example.issuetracker.Users.Entity.ChefAgence;
import com.example.issuetracker.Users.Repository.ChefAgenceRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ChefAgenceService {
    @Autowired
    private ChefAgenceRepository chefAgenceRepository;

    public ResponseEntity<ChefAgence> LoginChefgence(DTOLogin loginChefAgenceDTO){
        try {
            ValidInputLogin(loginChefAgenceDTO.getUserName(),loginChefAgenceDTO.getPassword());
            ChefAgence chefAgence=GetChefAgenceByUserName(loginChefAgenceDTO.getUserName());
            boolean status=CheckPasswordUser(chefAgence.getUserNameChefAgenc(),loginChefAgenceDTO.getPassword());
            System.out.println("\n\n\n status loginfunction : \t"+status);
            if (status){
                return new ResponseEntity<>(chefAgence,HttpStatus.OK);
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
    private ChefAgence GetChefAgenceByUserName(String UserName)
    {
        Optional<ChefAgence> optionalChefAgence=this.chefAgenceRepository.findChefAgenceBy_UserName(UserName);
        return optionalChefAgence.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,"Chef Agence Not Found"));
    }
    private Boolean CheckPasswordUser(String UserName,String  Password){
            ChefAgence chefAgence=GetChefAgenceByUserName(UserName);
            boolean status = BCrypt.checkpw(Password,chefAgence.getPasswordChefAgence());
            System.out.println("\n\n\n status checkpassword : \t"+status);
            if (status){
                    return true;
            }else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your Password is not Valide");
            }
        }


}
