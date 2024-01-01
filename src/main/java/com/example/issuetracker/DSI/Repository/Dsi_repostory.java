package com.example.issuetracker.DSI.Repository;

import com.example.issuetracker.DSI.Entity.DSI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface Dsi_repostory extends JpaRepository<DSI,String> {
    @Query("SELECT d FROM DSI d WHERE d.username_dsi = :username")
    DSI findByUsername_dsi(@Param("username") String username);

}
