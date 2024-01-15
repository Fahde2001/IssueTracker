package com.example.issuetracker.Users.Entity;

import com.example.issuetracker.DSI.Entity.DSI;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor
public class Employer {
    @Id
    private String idEmployer;
    @Column(unique = true)
    private String userNameEmployer;
    private String passwrodEmployer;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    //relation with table DSI
    @ManyToOne()
    @JoinColumn(name = "id_dsi")
    private DSI dsiemployer;
    //relation with table agence
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "idAgence")
    private Agence agence;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
