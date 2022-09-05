package com.game.controller;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum PlayerOrder {
    ID("id"), // default
    NAME("name"),
    EXPERIENCE("experience"),
    BIRTHDAY("birthday"),
    LEVEL("level");

    private final String fieldName;

    PlayerOrder(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }


}