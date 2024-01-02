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
public class chefAgence implements Serializable {
    @Id
    private String idChefAgenc;
    @Column(unique = true)
    private String userNameChefAgenc;
    private String passwordChefAgence;
    //relation with table dsi
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dsi")
    private DSI dsi;
    //relation with agence table
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAgence")
    private agence agence;
    //insert curent date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
