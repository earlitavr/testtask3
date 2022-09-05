package com.game.controller;

import com.game.entity.*;
import com.game.service.PlayerService;
import exceptions.CreatePlayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class PlayerController {


    //private PlayerService service = new PlayerServiceImpl();
    private PlayerService service;

    @Autowired
    public void setService(PlayerService service) {
        this.service = service;
    }

    @GetMapping(value = "/rest/players")
    public List<PlayerEntity> getPlayersList(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                             @RequestParam(value = "order", required = false, defaultValue = "ID") String order,
                                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                             @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                             @RequestParam(value = "race", required = false) String race,
                                             @RequestParam(value = "profession", required = false) String profession,
                                             @RequestParam(value = "after", required = false) Long after,
                                             @RequestParam(value = "before", required = false) Long before,
                                             @RequestParam(value = "banned", required = false) Boolean banned,
                                             @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                             @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                             @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                             @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        Specification spec = getSpecificationByParams(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);

        return service.getPageByParams(spec, PageRequest.of(pageNumber, pageSize, Sort.by(PlayerOrder.valueOf(order.toUpperCase()).getFieldName()))).toList();
    }

    @GetMapping(value = "/rest/players/count")
    public Integer getPlayersCount(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                   @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                   @RequestParam(value = "race", required = false) String race,
                                   @RequestParam(value = "profession", required = false) String profession,
                                   @RequestParam(value = "after", required = false) Long after,
                                   @RequestParam(value = "before", required = false) Long before,
                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {

        Specification spec = getSpecificationByParams(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);

        return service.getPlayersCount(spec);
    }

    @PostMapping(value = "/rest/players", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PlayerEntity createPlayer(@RequestBody PlayerEntity requestEntity) {
        if(requestEntity == null)
            throw new CreatePlayerException();

        if (requestEntity.getName() == null || requestEntity.getName().equals("") || requestEntity.getName().length() > 12 || requestEntity.getTitle().length() > 30 ||
                requestEntity.getBirthday().getTime() < 0 || requestEntity.getExperience() < 0 || requestEntity.getExperience() > 10000000 /*||
                requestEntity.getBirthday().getTime() < new Date(2000, Calendar.JANUARY, 1).getTime() ||
                requestEntity.getBirthday().getTime() > new Date(3000, Calendar.DECEMBER, 31).getTime()*/)
            throw new CreatePlayerException();

        /*PlayerEntity entity = new PlayerEntity(requestEntity.getName(), requestEntity.getTitle(), requestEntity.getRace(), requestEntity.getProfession(),
                requestEntity.getExperience(), requestEntity.getBirthday(), requestEntity.getBanned());*/
        requestEntity.businessLogic();

        return service.createPlayer(requestEntity);

    }


    @PostMapping(value = "/rest/players/{id}", consumes = "application/json", produces = "application/json")
    public PlayerEntity updatePlayer(@PathVariable long id, @RequestBody PlayerEntity requestEntity) {
        if(requestEntity == null)
            throw new CreatePlayerException();

        if (requestEntity.getName() != null && requestEntity.getName().length() > 12)
            throw new CreatePlayerException();

        if(requestEntity.getTitle() != null && requestEntity.getTitle().length() > 30)
            throw new CreatePlayerException();

        if(requestEntity.getExperience() != null && (requestEntity.getExperience() < 0 || requestEntity.getExperience() > 10000000))
            throw new CreatePlayerException();

        if(requestEntity.getBirthday() != null && (requestEntity.getBirthday().getTime() < 0 || requestEntity.getBirthday().getTime() < 946684800000L ||
                requestEntity.getBirthday().getTime() > 32535129600000L))
            throw new CreatePlayerException();


        return service.updatePlayer(id, requestEntity);
    }

    @DeleteMapping(value = "/rest/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        service.deletePlayer(id);
    }

    private Specification getSpecificationByParams(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                   @RequestParam(value = "title", required = false, defaultValue = "") String title,
                                                   @RequestParam(value = "race", required = false) String race,
                                                   @RequestParam(value = "profession", required = false) String profession,
                                                   @RequestParam(value = "after", required = false) Long after,
                                                   @RequestParam(value = "before", required = false) Long before,
                                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {

        PlayerSpecification spec = new PlayerSpecification();
        if(name != "")
            spec.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        if(title != "")
            spec.add(new SearchCriteria("title", title, SearchOperation.MATCH));
        if(race != null)
            spec.add(new SearchCriteria("race", Race.valueOf(race.toUpperCase()), SearchOperation.EQUAL));
        if(profession != null)
            spec.add(new SearchCriteria("profession", Profession.valueOf(profession.toUpperCase()), SearchOperation.EQUAL));
        if(maxExperience != null)
            spec.add(new SearchCriteria("experience", maxExperience, SearchOperation.LESS_THAN_EQUAL));
        if(minExperience != null)
            spec.add(new SearchCriteria("experience", minExperience, SearchOperation.GREATER_THAN_EQUAL));
        if(maxLevel != null)
            spec.add(new SearchCriteria("level", maxLevel, SearchOperation.LESS_THAN_EQUAL));
        if(minLevel != null)
            spec.add(new SearchCriteria("level", minLevel, SearchOperation.GREATER_THAN_EQUAL));
        if(banned != null)
            spec.add(new SearchCriteria("banned", banned, SearchOperation.EQUAL));
        if(before != null)
            spec.add(new SearchCriteria("birthday", new Date(before), SearchOperation.LESS_THAN_EQUAL_DATE));
        if(after != null)
            spec.add(new SearchCriteria("birthday", new Date(after), SearchOperation.GREATER_THAN_EQUAL_DATE));

        return spec;
    }



    @GetMapping(value = "/rest/players/{id}")
    public PlayerEntity getPlayer(@PathVariable long id) {
        return service.getPlayerById(id);
    }
}
