package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.ChefAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChefAgenceRepository extends JpaRepository<ChefAgence,String> {
    @Query("SELECT c from ChefAgence c where c.dsi.id_dsi=:IdDsi")
    List<ChefAgence> findAllChefAgenceByIdDSI(@Param("IdDsi") String IdDsi);
    @Query("SELECT c from ChefAgence c where c.userNameChefAgenc=:UserName")
    Optional<ChefAgence> findChefAgenceBy_UserName(@Param("UserName") String UserName);

}
