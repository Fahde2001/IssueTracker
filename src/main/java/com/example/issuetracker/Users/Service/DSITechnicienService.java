package com.example.issuetracker.Users.Service;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTO.DTOTechnicien.CreationTechnicienDTO;
import com.example.issuetracker.Users.DTO.DTOTechnicien.TechnicienDisplyDTO;
import com.example.issuetracker.Users.Entity.*;
import com.example.issuetracker.Users.Repository.TechnicienRepository;
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
public class DSITechnicienService {
    @Autowired
    private Dsi_repostory dsiRepostory;
    @Autowired
    private TechnicienRepository technicienRepository;


    public ResponseEntity<Boolean> AddUserTechnicien(String IdDsi, PasswordDsi passwordDsi, CreationTechnicienDTO creationTechnicienDTO) {
        try {
            validateInputs(IdDsi, passwordDsi.getPassword(),creationTechnicienDTO.getUserName(), creationTechnicienDTO.getPassword(),creationTechnicienDTO.getCategory());
            DSI dsi = validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            if(!ChekUserNameExsite(creationTechnicienDTO.getUserName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This username is alredy usng");
            Technicien technicien = createTechnicien(creationTechnicienDTO, dsi);
            this.technicienRepository.save(technicien);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    public ResponseEntity<List<TechnicienDisplyDTO>> GetAllEmployer(String IdDsi){
        try {
            validateDsi(IdDsi);
            DSI dsi=validateAndGetDsi(IdDsi);
            List<Technicien> techniciens=this.technicienRepository.findAllTechnicienByIdDSI(IdDsi);
            List<TechnicienDisplyDTO> TechnicienDisplay=techniciens.stream()
                    .map(this :: mapTechnicienDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(TechnicienDisplay,HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
    public ResponseEntity<Boolean> DeleteTechnicien(String IdDsi,String IdTechnicien,PasswordDsi passwordDsi){
        try{
            ValidateForDelete(IdDsi,IdTechnicien,passwordDsi);
            DSI dsi=validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            Technicien technicien=validateAndGetTechnicien(IdTechnicien);
            try {
                this.technicienRepository.deleteById(IdTechnicien);
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
    public ResponseEntity<Boolean> UpdateTechnicien(String IdDsi,String IdTechnicien,PasswordDsi passwordDsi,CreationTechnicienDTO creationTechnicienDTO){
        try{
            validateInputsForUpdate(IdDsi,IdTechnicien,passwordDsi.getPassword(),creationTechnicienDTO.getUserName(),creationTechnicienDTO.getPassword(),creationTechnicienDTO.getCategory());
            DSI dsi=validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            Technicien technicien=validateAndGetTechnicien(IdTechnicien);
            if(!ChekUserNameExsite(creationTechnicienDTO.getUserName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This username is alredy usng");
            technicien.setUserNameTechnicien(creationTechnicienDTO.getUserName());
            technicien.setPasswordTechnicien(BCrypt.hashpw(creationTechnicienDTO.getPassword(),BCrypt.gensalt()));
            boolean status;
            try {
                this.technicienRepository.save(technicien);
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
    private TechnicienDisplyDTO mapTechnicienDTO(Technicien technicien){
        TechnicienDisplyDTO dto=new TechnicienDisplyDTO();
        dto.setIdTechnicien(technicien.getIdTechnicien());
        dto.setUserName(technicien.getUserNameTechnicien());
        dto.setTypeUser(technicien.getTypeUser());
        dto.setIdDsi(technicien.getDsi().getId_dsi());
        dto.setCategory(technicien.getCategory());
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
    private void ValidateForDelete(String IdDsi,String IdTechnicien,PasswordDsi passwordDsi){
        if(IdDsi==null|| IdTechnicien==null|| passwordDsi.getPassword()==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Data is Empty");

    }
    private Boolean ChekUserNameExsite(String UserName){
        Optional<Technicien> optionalEmployer=this.technicienRepository.findTechnicienBy_UserName(UserName);
        boolean status;
        if(optionalEmployer.isPresent()){
            return status=false;
        }else{
            return status=true;
        }
    }
    private void validateDsi(String IdDsi){
        if(IdDsi == null)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your DSI is Empty");
    }
    private void validateInputs(String IdDsi, String DsiPassword, String userName, String TechnicienPassword, Category Category) {
        if (IdDsi == null || Category == null || DsiPassword == null ||
                userName == null || TechnicienPassword == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your Data is Empty");
        }
    }
    private void validateInputsForUpdate(String IdDsi, String IdTechnicien, String passwordDsi, String TechnicienUserName,String TechnicienPassword, Category Category) {
        if (IdDsi == null || Category == null || passwordDsi == null || IdTechnicien == null || TechnicienPassword == null || TechnicienUserName == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your Data is Empty");
        }
    }

    private Technicien validateAndGetTechnicien(String IdTechnicien){
        Optional<Technicien> optionalTechnicien=this.technicienRepository.findById(IdTechnicien);
        return optionalTechnicien.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"Technicien Not Found"));
    }

    private DSI validateAndGetDsi(String IdDsi) {
        Optional<DSI> optionalDSI = this.dsiRepostory.findById(IdDsi);
        return optionalDSI.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "DSI not found"));
    }


    private Boolean comparPassword(String IdDsi,String Password){
        DSI dsi=validateAndGetDsi(IdDsi);
        boolean status= BCrypt.checkpw(Password,dsi.getPassword_dsi());
        if(!status)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your password if Not validate");
        return true;
    }

    private Technicien createTechnicien(CreationTechnicienDTO creationTechnicienDTO, DSI dsi) {
        Technicien technicien = new Technicien();
        technicien.setIdTechnicien(UUID.randomUUID().toString());
        technicien.setUserNameTechnicien(creationTechnicienDTO.getUserName());
        technicien.setPasswordTechnicien(BCrypt.hashpw(creationTechnicienDTO.getPassword(),BCrypt.gensalt()));
        technicien.setDsi(dsi);
        technicien.setCategory(creationTechnicienDTO.getCategory());
        technicien.setTypeUser(TypeUser.Technicien);
        return technicien;
    }

}
