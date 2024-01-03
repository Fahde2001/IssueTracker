package com.example.issuetracker.Users.Function;


import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class FunctionDSI {

    private Dsi_repostory dsiRepostory;

    public FunctionDSI(Dsi_repostory dsiRepostory) {
        this.dsiRepostory = dsiRepostory;
    }

    public DSI FindDsi(String idDSI){
        try {
            System.out.println("Function IDDSI \t : "+idDSI);
            if(idDSI==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"IdDSI is Empty");
            Optional<DSI> optionalDSI = this.dsiRepostory.findById(idDSI);
            DSI dsi = optionalDSI.orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "DSI Not Found"));
            return dsi;
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }

    public Boolean ComparePassword(String Password,String IdDsi){
        try {
            if(Password==null || IdDsi==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your Paswword or IdDsi is Empty");
            DSI dsi=FindDsi(IdDsi);
            boolean status= BCrypt.checkpw(Password,dsi.getPassword_dsi());
            if (!status)throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Your Password is Not Correct");
            return true;
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }
    }
}
