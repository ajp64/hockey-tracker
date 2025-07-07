package com.ajp64.hockeytracker.repository;

import com.ajp64.hockeytracker.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    TeamEntity findByPublicId(String teamId);
    Set<TeamEntity> findAllByPublicIdIn(final Set<String> teamIds);
    @NonNull
    List<TeamEntity> findAll();
}
