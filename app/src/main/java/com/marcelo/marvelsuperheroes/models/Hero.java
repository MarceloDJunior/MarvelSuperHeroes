package com.marcelo.marvelsuperheroes.models;

import java.io.Serializable;

public class Hero implements Serializable {

    private int id;
    private String name;
    private String description;
    private HeroImage thumbnail;

    public Hero() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HeroImage getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(HeroImage thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
