package com.packt.microprofile.book.ch4.jsoncommon;

import java.util.List;

public class Starship {
    private String name;
    private boolean hasHyperdrive;
    private List<Weapon> weapons;
    private int speedRating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasHyperdrive() {
        return hasHyperdrive;
    }

    public void setHasHyperdrive(boolean hasHyperdrive) {
        this.hasHyperdrive = hasHyperdrive;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public int getSpeedRating() {
        return speedRating;
    }

    public void setSpeedRating(int speedRating) {
        this.speedRating = speedRating;
    }
}
