package com.example.issuetracker.DSI.Entity;

import com.example.issuetracker.Users.Entity.Employer;
import com.example.issuetracker.Users.Entity.agence;
import com.example.issuetracker.Users.Entity.chefAgence;
import com.example.issuetracker.Users.Entity.technicien;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor
public class DSI {
    @Id
    private String id_dsi;
    @Column(unique = true)
    private String username_dsi;
    private String password_dsi;
    private String name_company;
    //agences
    @OneToMany(mappedBy = "dsi")
    private List<agence> agences;
    //chefAgences
    @OneToMany(mappedBy = "dsi")
    private List<chefAgence> chefAgences;
    //employer
    @OneToMany(mappedBy = "dsiemployer")
    private List<Employer> employers;
    //technicien
    @OneToMany(mappedBy = "dsi")
    private List<technicien> techniciens;
    //curent date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

}
