package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenceRepository extends JpaRepository<Agence,String > {
    @Query("SELECT a FROM Agence a WHERE a.dsi.id_dsi = :dsiId")
    List<Agence> findAllAgencesByDsiId(@Param("dsiId") String dsiId);

    @Query("SELECT a FROM Agence a WHERE a.idAgence = :AgenceID AND a.dsi.id_dsi = :dsiId")
    Optional<Agence> findAllAgencesByDsiIdByAgenceId(@Param("dsiId") String dsiId, @Param("AgenceID") String AgenceID);

    @Query("select a from Agence a where a.name = :Name")
    Optional<Agence> findAgenceBy_Name(@Param("Name") String Name);





}
