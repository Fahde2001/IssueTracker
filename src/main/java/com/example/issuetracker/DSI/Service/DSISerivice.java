package com.example.issuetracker.DSI.Service;

import com.example.issuetracker.DSI.DTO.Create_dsi_dto;
import com.example.issuetracker.DSI.DTO.login_Dsi_DTO;
import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class DSISerivice {
    private final Dsi_repostory dsiRepostory;


    @Autowired
    public DSISerivice(Dsi_repostory dsiRepostory) {
        this.dsiRepostory = dsiRepostory;

    }

    public ResponseEntity<Boolean> AddUserDsi(Create_dsi_dto createDsiDto){
        try{
            DSI dsi =this.dsiRepostory.findByUsername_dsi(createDsiDto.getUserName());
            if(dsi!=null) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"this username "+createDsiDto.getUserName()+" is already exists");
            String hash= BCrypt.hashpw(createDsiDto.getPassword(), BCrypt.gensalt());
            DSI NewDsi=new DSI();
            System.out.println("username : "+createDsiDto.getUserName()+" password : "+createDsiDto.getUserName()+" name company : "+createDsiDto.getName_Company());
            NewDsi.setId_dsi(UUID.randomUUID().toString());
            NewDsi.setUsername_dsi(createDsiDto.getUserName());
            NewDsi.setPassword_dsi(hash);
            NewDsi.setName_company(createDsiDto.getName_Company());
            this.dsiRepostory.save(NewDsi);
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        } catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    public ResponseEntity<Boolean> loginDsi(login_Dsi_DTO loginDsiDto){
        try {
            if(loginDsiDto.getUserName()==null || loginDsiDto.getPassword()==null) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"your username or password is null");
            DSI dsi=this.dsiRepostory.findByUsername_dsi(loginDsiDto.getUserName());
            if (dsi == null) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"this username "+loginDsiDto.getUserName()+" is not found");
            Boolean ComparHash=BCrypt.checkpw(loginDsiDto.getPassword(),dsi.getPassword_dsi());
            if(!ComparHash) return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
            System.out.println("DSI \t"+dsi.getUsername_dsi() +" "+dsi.getId_dsi());
            return new ResponseEntity<>(true,HttpStatus.OK);
            }catch (ResponseStatusException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new RuntimeException("An error occurred", ex);
            }
    }
}
