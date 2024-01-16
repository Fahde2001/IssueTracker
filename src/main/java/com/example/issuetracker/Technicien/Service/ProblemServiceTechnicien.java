package com.example.issuetracker.Technicien.Service;

import com.example.issuetracker.Technicien.DTO.DTO_DiplayProblemTechnicien;
import com.example.issuetracker.Technicien.DTO.DTO_UpdateStatus;
import com.example.issuetracker.Users.Entity.Technicien;
import com.example.issuetracker.Users.Repository.TechnicienRepository;
import com.example.issuetracker.problem.Entity.Problem;
import com.example.issuetracker.problem.Entity.StatusProblem;
import com.example.issuetracker.problem.Repository.RepositoryProblem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProblemServiceTechnicien {
    @Autowired
    private RepositoryProblem repositoryProblem;
    @Autowired
    private TechnicienRepository technicienRepository;
    public ResponseEntity<List<DTO_DiplayProblemTechnicien>> GetProblemAssigmentTechnicien(String id_Technicen){
        try {
            Technicien technique=GetTechnicien(id_Technicen);
            List<Problem> listProblems=this.repositoryProblem.findAllProblemsTechnicienById(id_Technicen);
            List<DTO_DiplayProblemTechnicien> problemTechnicienList=listProblems.stream()
                    .map(this::mapProblemTechnicien)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(problemTechnicienList,HttpStatus.FOUND);
        }catch(ResponseStatusException ex){
            throw ex;
        }
        catch(Exception e){
            throw new RuntimeException("an error occurred while",e);
        }
    }
    public ResponseEntity<Problem> UpdateStatusProblem(String id_technicien, String id_Problem, DTO_UpdateStatus dtoUpdateStatus){
        try{
            Technicien technicien=GetTechnicien(id_technicien);
            Optional<Problem> optionalProblem=this.repositoryProblem.findById(id_Problem);
            Problem problem=optionalProblem.get();
            problem.setStatusProblem(dtoUpdateStatus.getStatus());
            problem.setDateRESOLVED(new Date());
            this.repositoryProblem.save(problem);
            return new ResponseEntity<>(problem,HttpStatus.OK);
        }catch (ResponseStatusException ex)
        {
            throw ex;
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"an error occurred while");
        }
    }
    private Technicien GetTechnicien(String id_Technicen){
        Optional<Technicien> optionalTechnicien=this.technicienRepository.findById(id_Technicen);
        return optionalTechnicien.orElseThrow(()-> new ResponseStatusException(HttpStatus.FORBIDDEN,"this Technicien does not exist"));
    }
    private DTO_DiplayProblemTechnicien mapProblemTechnicien(Problem problem){
        DTO_DiplayProblemTechnicien dto=new DTO_DiplayProblemTechnicien();
        dto.setId_Problem(problem.getId_problem());
        dto.setStatusProblem(problem.getStatusProblem().toString());
        dto.setDescription(problem.getDescription());
        dto.setAgenceName(problem.getAgence().getName());
        dto.setCreatedAt(problem.getCreatedAt());
        return dto;
    }
}
