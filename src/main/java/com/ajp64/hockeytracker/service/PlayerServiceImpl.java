package com.ajp64.hockeytracker.service;

import com.ajp64.hockeytracker.aspects.LogExecution;
import com.rest.server.model.Player;
import com.ajp64.hockeytracker.converter.PlayerEntityConverter;
import com.ajp64.hockeytracker.exceptions.NoNameException;
import com.ajp64.hockeytracker.model.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ajp64.hockeytracker.repository.PlayerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerEntityConverter converter;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PlayerEntityConverter converter) {
        this.playerRepository = playerRepository;
        this.converter = converter;
    }

    @Override
    @LogExecution
    public void createPlayer(PlayerEntity newPlayer)
    {
        if (newPlayer.getName() != null) {
            this.playerRepository.save(newPlayer);
        }
        else {
            throw new NoNameException("No player name provided");
        }
    }

    @Override
    public PlayerEntity getPlayer(String playerId) {
        return this.playerRepository.findByPublicId(playerId);
    }

    @Override
    public List<Player> getPlayers() {

        return this.playerRepository.findAll()
                .stream().map(converter::convert)
                .collect(Collectors.toList());
    }
}
