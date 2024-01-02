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
    @ManyToOne
    @JoinColumn(name = "id_dsi")
    private DSI dsiemployer;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;
}
