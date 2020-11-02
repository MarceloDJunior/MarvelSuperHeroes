package com.marcelo.marvelsuperheroes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HeroDataWrapper implements Serializable {

    private int code;
    private String status;
    private HeroDataContainer data;

    public HeroDataWrapper() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HeroDataContainer getData() {
        return data;
    }

    public void setData(HeroDataContainer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HeroDataWrapper{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
