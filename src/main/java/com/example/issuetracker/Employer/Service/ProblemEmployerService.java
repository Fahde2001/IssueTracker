package com.example.issuetracker.Employer.Service;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.TypeUser;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import com.example.issuetracker.Users.Repository.EmployerRepository;
import com.example.issuetracker.problem.DTO.DtoCreateProblem;
import com.example.issuetracker.problem.Entity.Problem;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.example.issuetracker.problem.Repository.RepositoryProblem;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProblemEmployerService {
    @Autowired
    private RepositoryProblem repositoryProblem;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private Dsi_repostory dsiRepostory;
    @Autowired
    private AgenceRepository agenceRepository;

    public ResponseEntity<Boolean> AddProblemEmployer(@NonNull String id_Employer, @org.springframework.lang.NonNull DtoCreateProblem dtoCreateProblem){
        try{
            Employer employer=GetEmployeById(id_Employer);
            DSI dsi=employer.getDsiemployer();
            Agence agence=employer.getAgence();
            Problem problemEmployer= new Problem();
            try {
                problemEmployer.setId_problem(UUID.randomUUID().toString());
                problemEmployer.setEmployer(employer);
                problemEmployer.setAgence(agence);
                problemEmployer.setDescription(dtoCreateProblem.getDescription());
                problemEmployer.setTypeUser(TypeUser.Employer);
                problemEmployer.setChefAgence(null);
                problemEmployer.setDsi(dsi);
                problemEmployer.setProblemCategory(dtoCreateProblem.getProblemCategory());
                problemEmployer.setTechnicien(null);
                problemEmployer.setStatusProblem(StatusProblem.NOT_RESOLVED);
                this.repositoryProblem.save(problemEmployer);
                return new ResponseEntity<>(true,HttpStatus.OK);
            }catch (EmptyResultDataAccessException e){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }

    }

    private Employer GetEmployeById(String id_Employer){
        Optional<Employer> employer=this.employerRepository.findById(id_Employer);
        return employer.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"this EMPLOYER not found"));

    }

}
