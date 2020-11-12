package com.jumbo1907.carrots.modes;

import com.jumbo1907.carrots.Carrots;

public abstract class Mode {

    private final String name;


    public Mode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void update();
}