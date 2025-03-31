package service;

import aspects.LogExecution;
import com.rest.server.model.Player;
import converter.PlayerEntityConverter;
import exceptions.NoPlayerNameException;
import model.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PlayerRepository;

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
        System.out.println("in service method");
        if (newPlayer.getName() != null) {
            this.playerRepository.storePlayer(newPlayer);
        }
        else {
            throw new NoPlayerNameException();
        }
    }

    @Override
    public PlayerEntity getPlayer(String playerId) {
        return this.playerRepository.getPlayer(playerId);
    }

    @Override
    public List<Player> getPlayers() {

        return this.playerRepository.getPlayers()
                .stream().map(converter::convert)
                .collect(Collectors.toList());
    }
}
