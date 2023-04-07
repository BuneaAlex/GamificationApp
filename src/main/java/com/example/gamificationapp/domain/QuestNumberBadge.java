package com.example.gamificationapp.domain;

import java.util.Objects;

public class QuestNumberBadge extends Badge{

    private int count;

    public QuestNumberBadge(String image,int count) {
        super(image);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestNumberBadge that = (QuestNumberBadge) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "QuestNumberBadge{" +
                "count=" + count +
                '}';
    }
}
