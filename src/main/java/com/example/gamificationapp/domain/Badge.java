package com.example.gamificationapp.domain;

import java.util.Objects;

public class Badge extends Entity<String>{

    private String image;

    public Badge(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(image, badge.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image);
    }

    @Override
    public String toString() {
        return "Badge{"+ getId() +"} " + getImage();
    }
}
