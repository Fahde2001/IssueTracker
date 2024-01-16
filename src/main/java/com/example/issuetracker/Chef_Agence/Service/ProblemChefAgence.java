package com.example.issuetracker.Chef_Agence.Service;

import com.example.issuetracker.Chef_Agence.DTO.DTO_DisplayChefAgence;
import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Employer.DTO.DTO_DisplayProblemEmployer;
import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.Users.Entity.ChefAgence;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.TypeUser;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import com.example.issuetracker.Users.Repository.ChefAgenceRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemChefAgence {
    @Autowired
    private RepositoryProblem repositoryProblem;
    @Autowired
    private ChefAgenceRepository chefAgenceRepository;
    @Autowired
    private Dsi_repostory dsiRepostory;
    @Autowired
    private AgenceRepository agenceRepository;

    public ResponseEntity<Boolean> AddProblemChefAgecne(@NonNull String id_ChefAgence, @org.springframework.lang.NonNull DtoCreateProblem dtoCreateProblem){
        try{
            ChefAgence chefAgence=GetChefAgence(id_ChefAgence);
            DSI dsi=chefAgence.getDsi();
            Agence agence=chefAgence.getAgence();
            Problem problemEmployer= new Problem();
            try {
                problemEmployer.setId_problem(UUID.randomUUID().toString());
                problemEmployer.setEmployer(null);
                problemEmployer.setAgence(agence);
                problemEmployer.setDescription(dtoCreateProblem.getDescription());
                problemEmployer.setTypeUser(TypeUser.ChefAgence);
                problemEmployer.setChefAgence(chefAgence);
                problemEmployer.setStatusProblem(StatusProblem.NOT_RESOLVED);
                problemEmployer.setProblemCategory(dtoCreateProblem.getProblemCategory());
                problemEmployer.setDsi(dsi);
                problemEmployer.setTechnicien(null);
                this.repositoryProblem.save(problemEmployer);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }catch (EmptyResultDataAccessException e){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (ResponseStatusException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred", ex);
        }

    }
    public ResponseEntity<List<DTO_DisplayChefAgence>> DisplayProblemChefAgence(String id_ChefAgence){
        try{
            List<Problem> list=GetProblemsChefAgenceById(id_ChefAgence);
            List<DTO_DisplayChefAgence> dtoDisplayChefAgences=list.stream()
                    .map(this::mapChefAgenceDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(dtoDisplayChefAgences,HttpStatus.FOUND);

        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("An error occurred ", ex);
        }
    }

    private ChefAgence GetChefAgence(String id_ChafAgence){
        Optional<ChefAgence> agence=this.chefAgenceRepository.findById(id_ChafAgence);
        return agence.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"this Chef Agence not found"));

    }

    private List<Problem> GetProblemsChefAgenceById(String id_ChefAgence){
        List<Problem> list=this.repositoryProblem.findAllProblemsByChefAgenceId(id_ChefAgence);
        if(list.isEmpty()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"we don't found problems for this Chef Agence");
        return list;
    }

    private DTO_DisplayChefAgence mapChefAgenceDTO(Problem problem) {
        DTO_DisplayChefAgence dto = new DTO_DisplayChefAgence();
        dto.setDescription(problem.getDescription());
        dto.setProblemCategory(problem.getProblemCategory());
        dto.setStatusProblem(problem.getStatusProblem());
        dto.setCreatedAt(problem.getCreatedAt());

        return dto;
    }


}
