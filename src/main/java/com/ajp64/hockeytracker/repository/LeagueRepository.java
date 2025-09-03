package com.ajp64.hockeytracker.repository;

import com.ajp64.hockeytracker.model.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {
    Optional<LeagueEntity> findByPublicId(final String leagueId);
    Set<LeagueEntity> findAllByPublicIdIn(final Set<String> leagueIds);
    @NonNull
    List<LeagueEntity> findAll();
}
