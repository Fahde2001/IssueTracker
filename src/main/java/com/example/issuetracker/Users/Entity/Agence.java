package com.example.issuetracker.Users.Entity;

import com.example.issuetracker.DSI.Entity.DSI;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Agence implements Serializable {
    @Id
    private String idAgence;
    @Column(unique = true)
    private String name;
    //relation with DSI table
    @ManyToOne
    @JoinColumn(name = "id_dsi")
    private DSI dsi;
    //relation with chefagence
    @OneToMany(mappedBy = "agence",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChefAgence> chefAgence;
    //relation with employe
    @OneToMany(mappedBy = "agence",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<Employer> employers;
    //date
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at",updatable = false)
    private Date createdAt;

}
