package com.example.issuetracker.problem.Repository;

import com.example.issuetracker.Users.Entity.Agence;
import com.example.issuetracker.problem.Entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryProblem extends JpaRepository<Problem,String> {

    @Query("SELECT p FROM Problem p WHERE p.employer.idEmployer = :id_employer")
    List<Problem> findAllProblemsByEmployerId(@Param("id_employer") String id_employer);

    @Query("SELECT p FROM Problem p WHERE p.chefAgence.idChefAgenc = :id_chefAgence")
    List<Problem> findAllProblemsByChefAgenceId(@Param("id_chefAgence") String id_chefAgence);

    @Query("SELECT p FROM Problem p WHERE p.agence.idAgence = :id_Agence")
    List<Problem> findAllProblemsAgenceById(@Param("id_Agence") String id_Agence);

    @Query("SELECT p FROM Problem p WHERE p.dsi.id_dsi = :id_DSI AND p.typeUser='Dsi' ")
    List<Problem> findAllProblemsDSIById(@Param("id_DSI") String id_DSI);

    @Query("SELECT p FROM Problem p WHERE p.dsi.id_dsi = :id_DSI AND p.typeUser <> 'Dsi'")
    List<Problem> findAllProblemsAgenceByIdDSI(@Param("id_DSI") String id_DSI);

    @Query("SELECT p FROM Problem p WHERE p.technicien.idTechnicien = :id_Technicien ")
    List<Problem> findAllProblemsTechnicienById(@Param("id_Technicien") String id_Technicien);


}
