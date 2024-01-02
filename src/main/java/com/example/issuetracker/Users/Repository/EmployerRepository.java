package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,String> {
}
