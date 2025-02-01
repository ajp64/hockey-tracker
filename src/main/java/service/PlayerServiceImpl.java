package service;

import aspects.LogExecution;
import model.Player;
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
    public void createPlayer(Player newPlayer) {
        this.playerRepository.storePlayer(newPlayer);
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
