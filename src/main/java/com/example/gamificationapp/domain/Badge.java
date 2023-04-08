package com.example.gamificationapp.domain;

import java.util.Objects;

/**
 * A serializable entity which has the ID of type String
 */

public class Badge extends Entity<String>{

    private String image;

    /**
     * Constructor
     * @param image - image name
     */

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
