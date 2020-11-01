package com.marcelo.marvelsuperheroes.models;

import java.io.Serializable;
import java.util.ArrayList;

public class HeroDataContainer implements Serializable {

    public ArrayList<Hero> results;
    public int count;
    public int total;

    public HeroDataContainer(){
    }

    @Override
    public String toString() {
        return "HeroDataContainer{" +
                "results=" + results +
                ", count=" + count +
                ", total=" + total +
                '}';
    }
}
