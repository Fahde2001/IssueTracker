package com.example.issuetracker.DSI.Service;

import com.example.issuetracker.Chef_Agence.DTO.DTO_DisplayChefAgence;
import com.example.issuetracker.DSI.DTO.DTO_DiplayProblemsAgenceToDsi;
import com.example.issuetracker.DSI.DTO.DTO_ProblemDSI;
import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.DSI.Repository.Dsi_repostory;
import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.Technicien;
import com.example.issuetracker.Users.Entity.TypeUser;
import com.example.issuetracker.Users.Repository.AgenceRepository;
import com.example.issuetracker.Users.Repository.EmployerRepository;
import com.example.issuetracker.Users.Repository.TechnicienRepository;
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

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProblemDSI {
    @Autowired
    private Dsi_repostory dsiRepostory;
    @Autowired
    private RepositoryProblem repositoryProblem;
    @Autowired
    private TechnicienRepository technicienRepository;

    public ResponseEntity<Boolean> AddProblemDSI(@NonNull String id_DSI, @org.springframework.lang.NonNull DtoCreateProblem dtoCreateProblem){
        try{
            DSI dsi=GetDsiById(id_DSI);

            Problem problemEmployer= new Problem();
            try {
                problemEmployer.setId_problem(UUID.randomUUID().toString());
                problemEmployer.setEmployer(null);
                problemEmployer.setAgence(null);
                problemEmployer.setDescription(dtoCreateProblem.getDescription());
                problemEmployer.setTypeUser(TypeUser.Dsi);
                problemEmployer.setChefAgence(null);
                problemEmployer.setDsi(dsi);
                problemEmployer.setStatusProblem(StatusProblem.NOT_RESOLVED);
                problemEmployer.setProblemCategory(dtoCreateProblem.getProblemCategory());
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

    public ResponseEntity<List<DTO_ProblemDSI>> DisplayProblemDSI(String id_DSI){
        try{
            List<Problem> list=GetProblemsDSIById(id_DSI);
            List<DTO_ProblemDSI> dtoProblemDSIS=list.stream()
                    .map(this::mapDsiProblemDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(dtoProblemDSIS,HttpStatus.FOUND);

        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("An error occurred ", ex);
        }
    }

    public ResponseEntity<List<DTO_DiplayProblemsAgenceToDsi>> GetAllProblemsAgenceToDSI(String id_DSI){
        try {
            List<Problem> list=GetProblemsAgenceByIdDSI(id_DSI);
            List<DTO_DiplayProblemsAgenceToDsi> dtoProblemDSIS=list.stream()
                   .map(this::mapProblemAgenceToDSIDTO)
                   .collect(Collectors.toList());
            return new ResponseEntity<>(dtoProblemDSIS,HttpStatus.FOUND);

        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("An error occurred ", ex);
        }

    }
    public ResponseEntity<Boolean> AssignmentsProblem(String id_DSI,String id_Problem,String id_Technecien){
        try
        {
            Technicien technicien=GetTechnicienById(id_Technecien);
            DSI dsi=GetDsiById(id_DSI);
            Problem problem=GetProblemById(id_Problem);
            problem.setTechnicien(technicien);
            this.repositoryProblem.save(problem);
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new RuntimeException("An error occurred ", ex);
        }
    }

    private DSI GetDsiById(String id_DSI){
        Optional<DSI> dsi=this.dsiRepostory.findById(id_DSI);
        return dsi.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"this DSI not found"));
    }
    private Technicien GetTechnicienById(String id_technicien){
        Optional<Technicien> optionalTechnicien=this.technicienRepository.findById(id_technicien);
        return optionalTechnicien.orElseThrow(()->new ResponseStatusException(HttpStatus.FORBIDDEN,"This Technicien Not FOUND"));
    }
    private Problem GetProblemById(String id_problem)
    {
        Optional<Problem> optionalProblem=this.repositoryProblem.findById(id_problem);
        return optionalProblem.orElseThrow(()->new ResponseStatusException(HttpStatus.FORBIDDEN,"This Problem Not FOUND"));
    }

    private List<Problem> GetProblemsDSIById(String id_DSI){
        List<Problem> list=this.repositoryProblem.findAllProblemsDSIById(id_DSI);
        if(list.isEmpty()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"we don't found problems for this DSI");
        return list;
    }

    private List<Problem> GetProblemsAgenceByIdDSI(String id_DSI){
        List<Problem> list=this.repositoryProblem.findAllProblemsAgenceByIdDSI(id_DSI);
        if(list.isEmpty()) throw  new ResponseStatusException(HttpStatus.FORBIDDEN,"we don't found problems for this DSI");
        return list;
    }

    private DTO_ProblemDSI mapDsiProblemDTO(Problem problem) {
        DTO_ProblemDSI dto = new DTO_ProblemDSI();
        dto.setId_Problem(problem.getId_problem());
        dto.setDescription(problem.getDescription());
        dto.setStatusProblem(problem.getStatusProblem());
        dto.setTechnicien(problem.getTechnicien());
        dto.setCreatedAt(problem.getCreatedAt());

        return dto;
    }

    private DTO_DiplayProblemsAgenceToDsi mapProblemAgenceToDSIDTO(Problem problem) {
        DTO_DiplayProblemsAgenceToDsi dto = new DTO_DiplayProblemsAgenceToDsi();
        dto.setId_Problem(problem.getId_problem());
        dto.setDescription(problem.getDescription());
        dto.setStatusProblem(problem.getStatusProblem());
        dto.setAgenceName(problem.getAgence().getName());
        dto.setTypeUser(problem.getTypeUser().toString());
        if(problem.getEmployer() !=null){
            dto.setUserName(problem.getEmployer().getUserNameEmployer());
        } else if (problem.getChefAgence()!=null) {
            dto.setUserName(problem.getChefAgence().getUserNameChefAgenc());
        }
        dto.setTechnicien(problem.getTechnicien());
        dto.setCreatedAt(problem.getCreatedAt());

        return dto;
    }

}
