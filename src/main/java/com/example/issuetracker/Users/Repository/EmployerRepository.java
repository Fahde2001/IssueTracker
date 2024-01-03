package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.chefAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,String> {
    @Query("SELECT e from Employer e where e.dsiemployer.id_dsi=:IdDsi")
    List<Employer> findAllEmpoyerByIdDSI(@Param("IdDsi") String IdDsi);
    @Query("SELECT e from Employer e where e.userNameEmployer=:UserName")
    Optional<Employer> findEmployerBy_UserName(@Param("UserName") String UserName);
}
