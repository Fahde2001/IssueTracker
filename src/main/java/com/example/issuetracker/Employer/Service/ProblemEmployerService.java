package com.example.issuetracker.Employer.Service;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Employer.DTO.DTO_DisplayProblemEmployer;
import com.example.issuetracker.Users.DTO.DTOEmployer.EmployerDisplyDTO;
import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.TypeUser;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import com.example.issuetracker.Users.Repository.EmployerRepository;
import com.example.issuetracker.problem.DTO.DtoCreateProblem;
import com.example.issuetracker.problem.Entity.Problem;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.example.issuetracker.problem.Repository.RepositoryProblem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NonNull;
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

    public ResponseEntity<List<DTO_DisplayProblemEmployer>> DisplayProblemEmployer(String id_Employer){
        try{
            List<Problem> list=GetProblemsEmployerById(id_Employer);
            List<DTO_DisplayProblemEmployer> dtoDisplayProblemEmployers=list.stream()
                    .map(this::mapEmployerDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(dtoDisplayProblemEmployers,HttpStatus.FOUND);

        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("An error occurred ", ex);
        }
    }

    private Employer GetEmployeById(String id_Employer){
        Optional<Employer> employer=this.employerRepository.findById(id_Employer);
        return employer.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"this EMPLOYER not found"));
    }

    private List<Problem> GetProblemsEmployerById(String id_Employer){
        List<Problem> list=this.repositoryProblem.findAllProblemsByEmployerId(id_Employer);
        if(list.isEmpty()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"we don't found problems for this employer");
        return list;
    }

    private DTO_DisplayProblemEmployer mapEmployerDTO(Problem problem) {
        DTO_DisplayProblemEmployer dto = new DTO_DisplayProblemEmployer();
        dto.setDescription(problem.getDescription());
        dto.setProblemCategory(problem.getProblemCategory());
        dto.setStatusProblem(problem.getStatusProblem());
        dto.setCreatedAt(problem.getCreatedAt());

        return dto;
    }

}
