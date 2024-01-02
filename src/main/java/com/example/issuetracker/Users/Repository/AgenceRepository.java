package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.Users.Entity.agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenceRepository extends JpaRepository<agence,String > {
    @Query("SELECT a FROM agence a WHERE a.dsi.id_dsi = :dsiId")
    List<agence> findAllAgencesByDsiId(@Param("dsiId") String dsiId);

    @Query("SELECT a FROM agence a WHERE a.idAgence = :AgenceID AND a.dsi.id_dsi = :dsiId")
    Optional<agence> findAllAgencesByDsiIdByAgenceId(@Param("dsiId") String dsiId, @Param("AgenceID") String AgenceID);





}
