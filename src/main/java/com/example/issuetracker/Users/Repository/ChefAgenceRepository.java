package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.chefAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChefAgenceRepository extends JpaRepository<chefAgence,String> {
    @Query("SELECT c from chefAgence c where c.dsi.id_dsi=:IdDsi")
    List<chefAgence> findAllChefAgenceByIdDSI(@Param("IdDsi") String IdDsi);
    @Query("SELECT c from chefAgence c where c.userNameChefAgenc=:UserName")
    Optional<chefAgence> findChefAgenceBy_UserName(@Param("UserName") String UserName);

}
