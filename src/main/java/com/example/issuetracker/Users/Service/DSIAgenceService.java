package com.example.issuetracker.Users.Service;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Users.DTO.DTOAgence.Creation_Agence_DTO;
import com.example.issuetracker.Users.DTO.DTOAgence.Display_Agence_DTO;
import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DSIAgenceService {
    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private Dsi_repostory dsiRepostory;
    public ResponseEntity<Boolean> addAgence(Creation_Agence_DTO creationAgenceDto, String Id_DSI){
        try{
            if(creationAgenceDto.getAgenceName()==null || Id_DSI==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Agence Name is Null");
            Optional<DSI> optionalDSI=this.dsiRepostory.findById(Id_DSI);
            if(!optionalDSI.isPresent()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Dsi Not found");
            DSI dsi=optionalDSI.get();
            if(!ChekUserNameExsite(creationAgenceDto.getAgenceName())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"This username is alredy usng");
            Agence agence=new Agence();
            agence.setIdAgence(UUID.randomUUID().toString());
            agence.setName(creationAgenceDto.getAgenceName());
            agence.setDsi(dsi);
            this.agenceRepository.save(agence);
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }  catch (ResponseStatusException ex) {
        throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    public ResponseEntity<List<Display_Agence_DTO>> ListAgenceByIdDsi(String idDsi) {
        if(idDsi==null)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your ID DSI is Null");
        Optional<DSI> optionalDSI=this.dsiRepostory.findById(idDsi);
        if(optionalDSI.isEmpty()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Dsi Not found");
        DSI dsi=optionalDSI.get();
        List<Agence> agenceList = this.agenceRepository.findAllAgencesByDsiId(dsi.getId_dsi());
        List<Display_Agence_DTO> displayAgenceDTOs = agenceList.stream()
                .map(agence -> mapAgenceToDTO(agence, dsi))
                .collect(Collectors.toList());

        return new ResponseEntity<>(displayAgenceDTOs, HttpStatus.OK);
    }
    public ResponseEntity<Agence> UpdateAgence(Creation_Agence_DTO creationAgenceDto, String idAgence, String idDsi){
        try {
            System.out.println("Service idAgence "+idAgence);
            System.out.println("Service idDSI "+idDsi);
            if(creationAgenceDto.getAgenceName()==null || idAgence==null || idDsi==null) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Agence Name is null");
            Optional<Agence> optionalAgence=this.agenceRepository.findAllAgencesByDsiIdByAgenceId(idDsi,idAgence);
            System.out.println("Agence :\t"+optionalAgence.get());
            if(!optionalAgence.isPresent()) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"this agence not founde");
            Agence agence=optionalAgence.get();
            agence.setName(creationAgenceDto.getAgenceName());
            Agence agenceUpdate=this.agenceRepository.save(agence);
            return new ResponseEntity<>(agenceUpdate,HttpStatus.OK);

        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
    public ResponseEntity<Boolean> DeletAgence(String idAgence, String idDSI, PasswordDsi passwordDsi){
        try{
            System.out.println("Service idAgence : "+idAgence);
            System.out.println("Service idDSI : "+idDSI);
            //chek if data is empty
            if(idAgence==null || idDSI==null || passwordDsi.getPassword()==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Data is Empty");
           //get dsi and controlle error
            Optional<DSI> optionalDSI=this.dsiRepostory.findById(idDSI);
            if(!optionalDSI.isPresent())throw new ResponseStatusException(HttpStatus.FORBIDDEN,"DSI not found");
            DSI dsi=optionalDSI.get();
            //compar password dsi
            if(!BCrypt.checkpw(passwordDsi.getPassword(),dsi.getPassword_dsi())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your password is not correct");
           //get agence by ID_DSI and ID_AGENCE and Controlle
            Optional<Agence> optionalAgence=this.agenceRepository.findAllAgencesByDsiIdByAgenceId(idDSI,idAgence);
            if(!optionalAgence.isPresent()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Agence is not found");
            Agence agence=optionalAgence.get();
            System.out.println("Agence for Delete id : "+agence.getIdAgence());
            try {
                this.agenceRepository.deleteById(agence.getIdAgence());
                System.out.println("Deletion successful");
                boolean status = true;
                return new ResponseEntity<>(status,HttpStatus.OK);
                } catch (EmptyResultDataAccessException e) {
                System.out.println("Agence not found for deletion");
                boolean status = false;
                return new ResponseEntity<>(status,HttpStatus.NOT_ACCEPTABLE);
                }
            } catch (ResponseStatusException ex) {
            throw ex;
            } catch (Exception ex) {
                throw new RuntimeException("An error occurred", ex);
            }
    }
    private Boolean ChekUserNameExsite(String Name){
        Optional<Agence> optionalAgence=this.agenceRepository.findAgenceBy_Name(Name);
        boolean status;
        if(optionalAgence.isPresent()){
            return status=false;
        }else{
            return status=true;
        }
    }
    public Agence findAgenceByDSI(String idDSI, String idAgence)
    {
        Optional<Agence> optionalAgence=this.agenceRepository.findAllAgencesByDsiIdByAgenceId(idDSI,idAgence);
        if(!optionalAgence.isPresent()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Agence is not found");
        Agence agence=optionalAgence.get();
        System.out.println("Agence for Delete id : "+agence.getIdAgence());
        return agence;
    }
    private Display_Agence_DTO mapAgenceToDTO(Agence agence, DSI dsi) {
        Display_Agence_DTO dto = new Display_Agence_DTO();
        dto.setIdAgence(agence.getIdAgence());
        dto.setName(agence.getName());
        dto.setId_dsi(dsi.getId_dsi());
        dto.setUsername_dsi(dsi.getUsername_dsi());
        // Format date
        String dateString = "2022-01-01"; // Replace this with your actual string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        dto.setCreatedAt(date);
        return dto;
    }

}
