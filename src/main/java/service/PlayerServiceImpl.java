package service;

import aspects.LogExecution;
import com.rest.server.model.Player;
import exceptions.NoPlayerNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    @LogExecution
    public void createPlayer(Player newPlayer)
    {
        if (newPlayer.getName() != null) {
            this.playerRepository.storePlayer(newPlayer);
        }
        else {
            throw new NoPlayerNameException();
        }
    }

    @Override
    public Player getPlayer(String playerId) {
        return this.playerRepository.getPlayer(playerId);
    }

    @Override
    public List<Player> getPlayers() {
        return this.playerRepository.getPlayers();
    }
}
