package repository;

import aspects.SecurityCheck;
import exceptions.PlayerNotFoundException;
import model.PlayerEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerRepositoryStubImpl implements PlayerRepository {

    private final List<PlayerEntity> playerList = new ArrayList<>();
    private final JdbcTemplate jdbc;
    public PlayerRepositoryStubImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @SecurityCheck
    public void storePlayer(PlayerEntity newPlayer) {
        String sql = "INSERT INTO players (name, dob, position) VALUES (?, ?, ?)";

        jdbc.update(sql, newPlayer.getName(), newPlayer.getDob(), newPlayer.getPosition());
    }

    @Override
    public PlayerEntity getPlayer(String playerId) throws PlayerNotFoundException {
        String sql = "SELECT * FROM players WHERE id = ?";

        RowMapper<PlayerEntity> playerRowMapper = (r, i) -> {
            PlayerEntity player = new PlayerEntity();
            player.setId(r.getLong("id"));
            player.setName(r.getString("name"));
            player.setDob(r.getString("dob"));
            player.setPosition(r.getString("position"));
            return player;
        };

        try {
            return jdbc.queryForObject(sql, playerRowMapper, playerId);
        } catch (EmptyResultDataAccessException e) {
            throw new PlayerNotFoundException("No player found for id " + playerId);
        }
    }

    @Override
    public List<PlayerEntity> getPlayers(){
        String sql = "SELECT * FROM players";

        RowMapper<PlayerEntity> playerRowMapper = (r, i) -> {
            PlayerEntity rowObject = new PlayerEntity();
            rowObject.setId(r.getLong("id"));
            rowObject.setName(r.getString("name"));
            rowObject.setDob(r.getString("dob"));
            rowObject.setPosition(r.getString("position"));
            return rowObject;
        };

        return jdbc.query(sql, playerRowMapper);
    }
}
