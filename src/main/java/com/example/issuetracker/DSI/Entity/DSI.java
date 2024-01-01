package com.example.issuetracker.DSI.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data@NoArgsConstructor@AllArgsConstructor
public class DSI {
    @Id
    private String id_dsi;
    private String username_dsi;
    private String password_dsi;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

}
