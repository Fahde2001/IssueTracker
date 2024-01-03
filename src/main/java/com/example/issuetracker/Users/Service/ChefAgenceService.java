package com.example.issuetracker.Users.Service;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Users.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTOChefAgence.ChefAgenceDisplyDTO;
import com.example.issuetracker.Users.DTOChefAgence.CretionChefAgenceDTO;
import com.example.issuetracker.Users.Entity.TypeUser;
import com.example.issuetracker.Users.Entity.agence;
import com.example.issuetracker.Users.Entity.chefAgence;
import com.example.issuetracker.Users.Function.FunctionAgence;
import com.example.issuetracker.Users.Function.FunctionDSI;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import com.example.issuetracker.Users.Repository.ChefAgenceRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChefAgenceService {

    private final AgenceRepository agenceRepository;
    private final Dsi_repostory dsiRepostory;
    private final ChefAgenceRepository chefAgenceRepository;
    private final FunctionDSI functionDSI;
    private final FunctionAgence functionAgence;

    @Autowired
    public ChefAgenceService(AgenceRepository agenceRepository, Dsi_repostory dsiRepostory,
                             ChefAgenceRepository chefAgenceRepository, FunctionDSI functionDSI,
                             FunctionAgence functionAgence) {
        this.agenceRepository = agenceRepository;
        this.dsiRepostory = dsiRepostory;
        this.chefAgenceRepository = chefAgenceRepository;
        this.functionDSI = functionDSI;
        this.functionAgence = functionAgence;
    }

    public ResponseEntity<Boolean> ADDUserChefAgence(String IdDsi, String IdAgence,
                                                     PasswordDsi passwordDsi,
                                                     CretionChefAgenceDTO cretionChefAgenceDTO) {
        try {
            validateInputs(IdDsi, IdAgence, passwordDsi.getPassword(),cretionChefAgenceDTO.getUserName(), cretionChefAgenceDTO.getPassword());
            DSI dsi = validateAndGetDsi(IdDsi);
            agence agence = validateAndGetAgence(IdDsi, IdAgence);
            comparPassword(IdDsi,passwordDsi.getPassword());
            chefAgence chefAgence = createChefAgence(cretionChefAgenceDTO, dsi, agence);
            this.chefAgenceRepository.save(chefAgence);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    public ResponseEntity<List<ChefAgenceDisplyDTO>> GetAllChefAgence(String IdDsi){
        try {
            validateDsi(IdDsi);
            DSI dsi=validateAndGetDsi(IdDsi);
            List<chefAgence> chefAgence=this.chefAgenceRepository.findAllChefAgenceByIdDSI(IdDsi);
           List<ChefAgenceDisplyDTO> chefAgenceDisply=chefAgence.stream()
                   .map(this :: mapChefAgenceDTO)
                   .collect(Collectors.toList());
            return new ResponseEntity<>(chefAgenceDisply,HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
    public ResponseEntity<Boolean> DeleteChefAgence(String IdDsi,String IdChefAgence,PasswordDsi passwordDsi){
        try{
            ValidateForDelete(IdDsi,IdChefAgence,passwordDsi);
            DSI dsi=validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            chefAgence chefAgence=validateAndGetChefAgence(IdChefAgence);
            try {
                this.chefAgenceRepository.deleteById(IdChefAgence);
                System.out.println("Deletion successful");
                boolean status = true;
                return new ResponseEntity<>(status,HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Agence not found for deletion");
                boolean status = false;
                return new ResponseEntity<>(status,HttpStatus.NOT_ACCEPTABLE);
            }
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
    public ResponseEntity<Boolean> UpdateChefAgence(String IdDsi,String IdChefAgence,PasswordDsi passwordDsi,CretionChefAgenceDTO cretionChefAgenceDTO){
        try{
            validateInputs(IdDsi,IdChefAgence,passwordDsi.getPassword(),cretionChefAgenceDTO.getUserName(),cretionChefAgenceDTO.getPassword());
            DSI dsi=validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            chefAgence chefAgence=validateAndGetChefAgence(IdChefAgence);
            if(!ChekUserNameExsite(cretionChefAgenceDTO.getUserName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This username is alredy usng");
            chefAgence.setUserNameChefAgenc(cretionChefAgenceDTO.getUserName());
            chefAgence.setPasswordChefAgence(BCrypt.hashpw(cretionChefAgenceDTO.getPassword(),BCrypt.gensalt()));
            boolean status;
            try {
                this.chefAgenceRepository.save(chefAgence);
                System.out.println("Update successful");
                 status = true;
                return new ResponseEntity<>(status,HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                System.out.println("Agence not found for Updating");
                status = false;
                return new ResponseEntity<>(status,HttpStatus.NOT_ACCEPTABLE);
            }

        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
    private ChefAgenceDisplyDTO mapChefAgenceDTO(chefAgence chefAgence){
        ChefAgenceDisplyDTO dto=new ChefAgenceDisplyDTO();
        dto.setIdAgence(chefAgence.getAgence().getIdAgence());
        dto.setIdChefAgence(chefAgence.getIdChefAgenc());
        dto.setUserName(chefAgence.getUserNameChefAgenc());
        dto.setTypeUser(chefAgence.getTypeUser());
        dto.setIdDsi(chefAgence.getDsi().getId_dsi());
        dto.setNameAgence(chefAgence.getAgence().getName());
        String dateString = "2022-01-01"; // Replace this with your actual string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        dto.setCreateAt(date);
        return dto;
    }
    private void ValidateForDelete(String IdDsi,String IdChefAgence,PasswordDsi passwordDsi){
        if(IdDsi==null|| IdChefAgence==null|| passwordDsi.getPassword()==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Data is Empty");

    }
    private Boolean ChekUserNameExsite(String UserName){
        Optional<chefAgence> optionalChefAgence=this.chefAgenceRepository.findChefAgenceBy_UserName(UserName);
        boolean status;
        if(optionalChefAgence.isPresent()){
            return status=false;
        }else{
            return status=true;
        }
    }
    private void validateDsi(String IdDsi){
        if(IdDsi == null)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your DSI is Empty");
    }
    private void validateInputs(String IdDsi, String IdAgence, String password,
                                String userName, String chefAgencePassword) {
        if (IdDsi == null || IdAgence == null || password == null ||
                userName == null || chefAgencePassword == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your Data is Empty");
        }
    }
    private chefAgence validateAndGetChefAgence(String IdChefAgenc){
        Optional<chefAgence> optionalChefAgence=this.chefAgenceRepository.findById(IdChefAgenc);
        return optionalChefAgence.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"ChefAgence Not Found"));
    }

    private DSI validateAndGetDsi(String IdDsi) {
        Optional<DSI> optionalDSI = this.dsiRepostory.findById(IdDsi);
        return optionalDSI.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "DSI not found"));
    }

    private agence validateAndGetAgence(String IdDsi, String IdAgence) {
        return this.agenceRepository.findAllAgencesByDsiIdByAgenceId(IdDsi, IdAgence)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Agence not found"));
    }
    private Boolean comparPassword(String IdDsi,String Password){
        DSI dsi=validateAndGetDsi(IdDsi);
        boolean status= BCrypt.checkpw(Password,dsi.getPassword_dsi());
        if(!status)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your password if Not validate");
        return true;
    }

    private chefAgence createChefAgence(CretionChefAgenceDTO cretionChefAgenceDTO, DSI dsi, agence agence) {
        chefAgence chefAgence = new chefAgence();
        chefAgence.setIdChefAgenc(UUID.randomUUID().toString());
        chefAgence.setUserNameChefAgenc(cretionChefAgenceDTO.getUserName());
        chefAgence.setPasswordChefAgence(BCrypt.hashpw(cretionChefAgenceDTO.getPassword(),BCrypt.gensalt()));
        chefAgence.setDsi(dsi);
        chefAgence.setAgence(agence);
        chefAgence.setTypeUser(TypeUser.ChefAgence);
        return chefAgence;
    }
}
