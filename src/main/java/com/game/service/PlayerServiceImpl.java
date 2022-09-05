package com.game.service;

import com.game.entity.PlayerEntity;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import exceptions.IdNotExistException;
import exceptions.WrongIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    PlayerRepository playerRepository;

    @Autowired
    public void setPlayerRepository(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    @Transactional
    public List<PlayerEntity> getAll() {
        return playerRepository.findAll();
    }

    @Override
    @Transactional
    public Integer getPlayersCount(Specification specification) {
        return playerRepository.findAll(specification).size();
    }

    @Override
    @Transactional
    public PlayerEntity getPlayerById(long id) {
        if(id <= 0)
            throw new WrongIdException();

        Optional<PlayerEntity> optional = playerRepository.findById(id);
        if(!optional.isPresent())
            throw new IdNotExistException();

        return playerRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Page<PlayerEntity> getPageByParams(Specification specification, Pageable pageable) {

        return playerRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public PlayerEntity createPlayer(PlayerEntity entity) {
        return playerRepository.save(entity);
    }

    @Override
    public void deletePlayer(Long id) {
        if(id <= 0)
            throw new WrongIdException();

        Optional<PlayerEntity> optional = playerRepository.findById(id);
        if(!optional.isPresent())
            throw new IdNotExistException();

        playerRepository.deleteById(id);
    }

    @Override
    public PlayerEntity updatePlayer(Long id, PlayerEntity entity) {
        if(id <= 0)
            throw new WrongIdException();

        Optional<PlayerEntity> optional = playerRepository.findById(id);
        if(!optional.isPresent())
            throw new IdNotExistException();

        PlayerEntity oldEntity = playerRepository.findById(id).get();
        if(entity.getName() != null)
            oldEntity.setName(entity.getName());
        if(entity.getTitle() != null)
            oldEntity.setTitle(entity.getTitle());
        if(entity.getRace() != null)
            oldEntity.setRace(entity.getRace());
        if(entity.getProfession() != null)
            oldEntity.setProfession(entity.getProfession());
        if(entity.getBirthday() != null)
            oldEntity.setBirthday(entity.getBirthday());
        if(entity.getBanned() != null)
            oldEntity.setBanned(entity.getBanned());
        if(entity.getExperience() != null)
            oldEntity.setExperience(entity.getExperience());

        oldEntity.businessLogic();

        return playerRepository.save(oldEntity);
    }
}
