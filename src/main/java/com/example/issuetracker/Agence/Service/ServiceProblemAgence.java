package com.example.issuetracker.Agence.Service;

import com.example.issuetracker.Agence.DTO.DTO_DisplayProblemAgence;
import com.example.issuetracker.Chef_Agence.DTO.DTO_DisplayChefAgence;
import com.example.issuetracker.Users.Entity.ChefAgence;
import com.example.issuetracker.Users.Repository.ChefAgenceRepository;
import com.example.issuetracker.problem.Entity.Problem;
import com.example.issuetracker.problem.Repository.RepositoryProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceProblemAgence {
    @Autowired
    private RepositoryProblem repositoryProblem;
    @Autowired
    private ChefAgenceRepository chefAgenceRepository;

    public ResponseEntity<List<DTO_DisplayProblemAgence>> GetAllProlemsAgence(String id_ChefAgence){
        try {
            Optional<ChefAgence> optionalChefAgence=this.chefAgenceRepository.findById(id_ChefAgence);
            if(!optionalChefAgence.isPresent()) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Chef Agence is not founde");
            ChefAgence chefAgence=optionalChefAgence.get();
            List<Problem> list=GetAllProblemAgenceByChefAgence(chefAgence.getAgence().getIdAgence());
            List<DTO_DisplayProblemAgence> list_problem=list.stream()
                    .map(this::mapAgenceProblemDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(list_problem,HttpStatus.FOUND);

        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("An error occurred ", ex);
        }
    }

    private List<Problem> GetAllProblemAgenceByChefAgence(String id_Agence) {
        List<Problem> list=this.repositoryProblem.findAllProblemsAgenceById(id_Agence);
        if (list.isEmpty()) throw new ResponseStatusException(HttpStatus.OK,"Not Found Problem");
        return list;
    }
    private DTO_DisplayProblemAgence mapAgenceProblemDTO(Problem problem) {
        DTO_DisplayProblemAgence dto = new DTO_DisplayProblemAgence();
        if (problem.getAgence()!=null) {
            dto.setIdProblem(problem.getId_problem());
            dto.setDescription(problem.getDescription());
            dto.setProblemCategory(problem.getProblemCategory());
           if (problem.getEmployer() != null) {
                dto.setUserName(problem.getEmployer().getUserNameEmployer());
            } else {
                dto.setUserName(problem.getChefAgence().getUserNameChefAgenc());
            }
           dto.setTypeUser(problem.getTypeUser().toString());
            dto.setStatusProblem(problem.getStatusProblem());
            dto.setCreatedAt(problem.getCreatedAt());
        }
        return dto;
    }


}
