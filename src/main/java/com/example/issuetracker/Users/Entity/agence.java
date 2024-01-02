package com.example.issuetracker.Users.Entity;

import com.example.issuetracker.DSI.Entity.DSI;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor@AllArgsConstructor
public class agence implements Serializable {
    @Id
    private String idAgence;
    private String name;
    //relation with DSI table
    @ManyToOne
    @JoinColumn(name = "id_dsi")
    private DSI dsi;
    //date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",updatable = false)
    private Date createdAt;

}
