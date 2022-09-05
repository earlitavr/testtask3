package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PlayerService {
    List<PlayerEntity> getAll();
    Integer getPlayersCount(Specification specification);

    PlayerEntity getPlayerById(long id);

    Page<PlayerEntity> getPageByParams(Specification specification, Pageable pageable);

    PlayerEntity createPlayer(PlayerEntity entity);

    void deletePlayer(Long id);

    PlayerEntity updatePlayer(Long id, PlayerEntity entity);
}
