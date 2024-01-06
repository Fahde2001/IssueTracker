package com.example.issuetracker.Users.Service;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTO.DTOEmployer.CreationEmployerDTO;
import com.example.issuetracker.Users.DTO.DTOEmployer.EmployerDisplyDTO;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.TypeUser;
import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import com.example.issuetracker.Users.Repository.EmployerRepository;
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
public class DSIEmployeService {
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private Dsi_repostory dsiRepostory;
    @Autowired
    private AgenceRepository agenceRepository;


    public ResponseEntity<Boolean> AddUserEmployer(String IdDsi, String IdAgence,
                                                     PasswordDsi passwordDsi,
                                                     CreationEmployerDTO creationEmployerDTO) {
        try {
            validateInputs(IdDsi, IdAgence, passwordDsi.getPassword(),creationEmployerDTO.getUserName(), creationEmployerDTO.getPassword());
            DSI dsi = validateAndGetDsi(IdDsi);
            Agence agence = validateAndGetAgence(IdDsi, IdAgence);
            comparPassword(IdDsi,passwordDsi.getPassword());
            if(!ChekUserNameExsite(creationEmployerDTO.getUserName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This username is alredy usng");
            Employer employer = createEmployer(creationEmployerDTO, dsi, agence);
            this.employerRepository.save(employer);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    public ResponseEntity<List<EmployerDisplyDTO>> GetAllEmployer(String IdDsi){
        try {
            validateDsi(IdDsi);
            DSI dsi=validateAndGetDsi(IdDsi);
            List<Employer> employers=this.employerRepository.findAllEmpoyerByIdDSI(IdDsi);
            List<EmployerDisplyDTO> employerDisply=employers.stream()
                    .map(this :: mapEmployerDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(employerDisply,HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
    public ResponseEntity<Boolean> DeleteEmployer(String IdDsi,String IdEmploye,PasswordDsi passwordDsi){
        try{
            ValidateForDelete(IdDsi,IdEmploye,passwordDsi);
            DSI dsi=validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            Employer employer=validateAndGetEmployer(IdEmploye);
            try {
                this.employerRepository.deleteById(IdEmploye);
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
    public ResponseEntity<Boolean> UpdateEmployer(String IdDsi,String IdEmployer,PasswordDsi passwordDsi,CreationEmployerDTO creationEmployerDTO){
        try{
            validateInputs(IdDsi,IdEmployer,passwordDsi.getPassword(),creationEmployerDTO.getUserName(),creationEmployerDTO.getPassword());
            DSI dsi=validateAndGetDsi(IdDsi);
            comparPassword(IdDsi,passwordDsi.getPassword());
            Employer employer=validateAndGetEmployer(IdEmployer);
            if(!ChekUserNameExsite(creationEmployerDTO.getUserName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This username is alredy usng");
            employer.setUserNameEmployer(creationEmployerDTO.getUserName());
            employer.setPasswrodEmployer(BCrypt.hashpw(creationEmployerDTO.getPassword(),BCrypt.gensalt()));
            boolean status;
            try {
                this.employerRepository.save(employer);
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
    private EmployerDisplyDTO mapEmployerDTO(Employer employer){
        EmployerDisplyDTO dto=new EmployerDisplyDTO();
        dto.setIdAgence(employer.getAgence().getIdAgence());
        dto.setIdEmployer(employer.getIdEmployer());
        dto.setUserName(employer.getUserNameEmployer());
        dto.setTypeUser(employer.getTypeUser());
        dto.setIdDsi(employer.getDsiemployer().getId_dsi());
        dto.setNameAgence(employer.getAgence().getName());
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
    private void ValidateForDelete(String IdDsi,String IdEmployer,PasswordDsi passwordDsi){
        if(IdDsi==null|| IdEmployer==null|| passwordDsi.getPassword()==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Data is Empty");

    }
    private Boolean ChekUserNameExsite(String UserName){
        Optional<Employer> optionalEmployer=this.employerRepository.findEmployerBy_UserName(UserName);
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
    private void validateInputs(String IdDsi, String IdAgence, String password,
                                String userName, String chefAgencePassword) {
        if (IdDsi == null || IdAgence == null || password == null ||
                userName == null || chefAgencePassword == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Your Data is Empty");
        }
    }
    private Employer validateAndGetEmployer(String IdEmpoyer){
        Optional<Employer> optionalEmployer=this.employerRepository.findById(IdEmpoyer);
        return optionalEmployer.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"ChefAgence Not Found"));
    }

    private DSI validateAndGetDsi(String IdDsi) {
        Optional<DSI> optionalDSI = this.dsiRepostory.findById(IdDsi);
        return optionalDSI.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "DSI not found"));
    }

    private Agence validateAndGetAgence(String IdDsi, String IdAgence) {
        return this.agenceRepository.findAllAgencesByDsiIdByAgenceId(IdDsi, IdAgence)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Agence not found"));
    }
    private Boolean comparPassword(String IdDsi,String Password){
        DSI dsi=validateAndGetDsi(IdDsi);
        boolean status= BCrypt.checkpw(Password,dsi.getPassword_dsi());
        if(!status)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your password if Not validate");
        return true;
    }

    private Employer createEmployer(CreationEmployerDTO creationEmployerDTO, DSI dsi, Agence agence) {
        Employer employer = new Employer();
        employer.setIdEmployer(UUID.randomUUID().toString());
        employer.setUserNameEmployer(creationEmployerDTO.getUserName());
        employer.setPasswrodEmployer(BCrypt.hashpw(creationEmployerDTO.getPassword(),BCrypt.gensalt()));
        employer.setDsiemployer(dsi);
        employer.setAgence(agence);
        employer.setTypeUser(TypeUser.Employer);
        return employer;
    }

}
