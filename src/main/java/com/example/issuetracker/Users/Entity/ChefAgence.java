package com.example.issuetracker.Users.Entity;

import com.example.issuetracker.DSI.Entity.DSI;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor@AllArgsConstructor
public class ChefAgence implements Serializable {
    @Id
    private String idChefAgenc;
    @Column(unique = true)
    private String userNameChefAgenc;
    private String passwordChefAgence;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    //relation with table dsi
    @ManyToOne()
    @JoinColumn(name = "id_dsi")
    private DSI dsi;
    //relation with agence table
    @ManyToOne()
    @JoinColumn(name = "idAgence")
    private Agence agence;
    //insert curent date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
