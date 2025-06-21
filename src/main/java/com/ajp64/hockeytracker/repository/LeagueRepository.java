package com.ajp64.hockeytracker.repository;

import com.ajp64.hockeytracker.model.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
    LeagueEntity findByPublicId(String leagueId);
    @NonNull
    List<LeagueEntity> findAll();
}
