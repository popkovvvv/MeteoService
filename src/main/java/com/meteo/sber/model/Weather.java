package com.meteo.sber.model;

import javax.persistence.*;

@Entity
@Table
public class Weather {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String main;

    @Column
    private String description;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}