package com.example.issuetracker.problem.Entity;

import com.example.issuetracker.DSI.Entity.DSI;
import com.example.issuetracker.Users.Entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor
public class Problem {
    @Id
    private String id_problem;
    private String description;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    @Enumerated(EnumType.STRING)
    private ProblemCategory problemCategory;
    @Enumerated(EnumType.STRING)
    private StatusProblem statusProblem;

    @ManyToOne()
    @JoinColumn(name = "id_dsi")
    private DSI dsi;

    @ManyToOne()
    @JoinColumn(name ="idChefAgenc" )
    private ChefAgence chefAgence;

    @ManyToOne()
    @JoinColumn(name = "idEmployer")
    private Employer employer;

    @ManyToOne()
    @JoinColumn(name = "idAgence")
    private Agence agence;

    @ManyToOne()
    @JoinColumn(name = "idTechnicien")
    private Technicien technicien;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;


    private Date DateRESOLVED;
}
