package converter;

import com.rest.server.model.Player;
import model.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerEntityConverter {

    public Player convert(PlayerEntity playerEntity)
    {
        final Player retval = new Player();

        retval.name(playerEntity.getName());
        retval.dob(playerEntity.getDob());
        retval.position(playerEntity.getPosition());

        return retval;
    }

}
