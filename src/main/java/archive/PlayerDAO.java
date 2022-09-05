package archive;

import com.game.entity.PlayerEntity;

import java.util.List;

public interface PlayerDAO {
    List<PlayerEntity> getAllPlayers();
    List<PlayerEntity> getPlayersById();
}
