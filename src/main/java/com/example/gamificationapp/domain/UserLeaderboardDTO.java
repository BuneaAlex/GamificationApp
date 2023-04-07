package com.example.gamificationapp.domain;

import java.util.Objects;

public class UserLeaderboardDTO {

    private User user;
    private int rank;
    private int tokens;

    public UserLeaderboardDTO(User user, int rank, int tokens) {
        this.user = user;
        this.rank = rank;
        this.tokens = tokens;
    }

    public User getUser() {
        return user;
    }

    public int getRank() {
        return rank;
    }

    public int getTokens() {
        return tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLeaderboardDTO that = (UserLeaderboardDTO) o;
        return user == that.user && rank == that.rank && tokens == that.tokens;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, rank, tokens);
    }

    @Override
    public String toString() {
        return "UserLeaderboardDTO{" +
                "user=" + user +
                ", rank=" + rank +
                ", tokens=" + tokens +
                '}';
    }
}
