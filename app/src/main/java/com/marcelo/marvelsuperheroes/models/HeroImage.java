package com.marcelo.marvelsuperheroes.models;

public class HeroImage {

    private String path;
    private String extension;

    public HeroImage() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "HeroImage{" +
                "path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
