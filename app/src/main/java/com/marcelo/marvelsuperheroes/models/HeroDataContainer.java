package com.marcelo.marvelsuperheroes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class HeroDataContainer implements Serializable {

    private ArrayList<Hero> results;
    private int count;
    private int total;

    public HeroDataContainer(){
    }

    public ArrayList<Hero> getResults() {
        return results;
    }

    public void setResults(ArrayList<Hero> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
