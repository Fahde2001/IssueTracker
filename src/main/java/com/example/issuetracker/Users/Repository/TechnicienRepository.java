package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicienRepository extends JpaRepository<technicien,String> {
}
