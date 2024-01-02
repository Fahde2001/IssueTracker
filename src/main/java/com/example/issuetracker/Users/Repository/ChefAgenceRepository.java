package com.example.issuetracker.Users.Repository;

import com.example.issuetracker.Users.Entity.chefAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChefAgenceRepository extends JpaRepository<chefAgence,String> {
}
