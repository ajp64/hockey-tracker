package com.ajp64.hockeytracker.repository;

import com.ajp64.hockeytracker.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    PlayerEntity findByPublicId(String playerId);
    Set<PlayerEntity> findAllByPublicIdIn(final Set<String> playerIds);
    @NonNull
    List<PlayerEntity> findAll();
}
