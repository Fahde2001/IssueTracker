package com.example.issuetracker.Users.Function;

import com.example.issuetracker.Users.Entity.agence;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class FunctionAgence {

    private AgenceRepository agenceRepository;
    public FunctionAgence(AgenceRepository agenceRepository) {
        this.agenceRepository = agenceRepository;
    }

    public agence FindAgenceByIdDsiIdAgence(String IdDsi,String IdAgence)
    {
        try {
            if(IdAgence==null || IdDsi==null) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"Your IdAgence or IdDsi is empty");
            Optional<agence> optionalAgence=this.agenceRepository.findAllAgencesByDsiIdByAgenceId(IdDsi,IdAgence);
            if(!optionalAgence.isPresent()) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"this agence not founde");
            agence agence=optionalAgence.get();
            return agence;
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }

    }
}
