package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicienRepository extends JpaRepository<Technicien,String> {
    @Query("SELECT T from Technicien T where T.dsi.id_dsi=:IdDsi")
    List<Technicien> findAllTechnicienByIdDSI(@Param("IdDsi") String IdDsi);
    @Query("SELECT T from Technicien T where T.userNameTechnicien=:UserName")
    Optional<Technicien> findTechnicienBy_UserName(@Param("UserName") String UserName);
}
