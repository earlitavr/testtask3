package com.game.entity;

import com.game.controller.PlayerOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToOrderEnumConverter implements Converter<String, PlayerOrder> {
    @Override
    public PlayerOrder convert(String source) {
        try {
            return PlayerOrder.valueOf(source);
        } catch(Exception e) {
            return PlayerOrder.ID;
        }
    }
}
