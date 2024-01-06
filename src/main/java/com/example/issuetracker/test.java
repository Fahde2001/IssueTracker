package com.example.issuetracker;

import com.example.issuetracker.Users.DTO.DTOAgence.PasswordDsi;
import com.example.issuetracker.Users.DTO.DTOTechnicien.CreationTechnicienDTO;
import com.example.issuetracker.Users.Entity.Category;
import com.example.issuetracker.Users.Service.DSITechnicienService;
import org.springframework.http.ResponseEntity;

public class test {
    public static void main(String[] args){
        System.out.println("Hello");
        DSITechnicienService technicienService=new DSITechnicienService();
        CreationTechnicienDTO creationTechnicienDTO=new CreationTechnicienDTO();
        PasswordDsi passwordDsi=new PasswordDsi();
        String idDsi="616e2211-733d-4f94-8da7-b6ab435a2be1";
        passwordDsi.setPassword("123456");
        creationTechnicienDTO.setPassword("Test");
        creationTechnicienDTO.setUserName("FAHDE FAHDE");
        creationTechnicienDTO.setCategory(Category.Responsable_Sécurité_Informatique);

        ResponseEntity<Boolean> response = technicienService.AddUserTechnicien(idDsi, passwordDsi, creationTechnicienDTO);

        // Print the result
        System.out.println("Response: " + response.getBody());


    }
}
