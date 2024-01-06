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
public class Technicien implements Serializable {
    @Id
    private String idTechnicien;
    @Column(unique = true)
    private String userNameTechnicien;
    private String passwordTechnicien;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    @Enumerated(EnumType.STRING)
    private Category category;
    //relation with tadable DSI
    @ManyToOne
    @JoinColumn(name = "id_dsi")
    private DSI dsi;
    //insert curent date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
