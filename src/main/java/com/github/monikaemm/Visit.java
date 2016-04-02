package com.github.monikaemm;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Monika Hołysz on 02.04.2016.
 */
public class Visit {
    private LocalDateTime date;
    private String name;
    private String species;
    private String purpose;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
