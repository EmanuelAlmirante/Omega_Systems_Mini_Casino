package model;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private double chanceOfWinning;

    private double winningMultiplier;

    private int maxBet;

    private int minBet;

    public Game() {
    }

    public Game(long id, String name, double chanceOfWinning, double winningMultiplier, int maxBet, int minBet) {
        this.id = id;
        this.name = name;
        this.chanceOfWinning = chanceOfWinning;
        this.winningMultiplier = winningMultiplier;
        this.maxBet = maxBet;
        this.minBet = minBet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChanceOfWinning() {
        return chanceOfWinning;
    }

    public void setChanceOfWinning(double chanceOfWinning) {
        this.chanceOfWinning = chanceOfWinning;
    }

    public double getWinningMultiplier() {
        return winningMultiplier;
    }

    public void setWinningMultiplier(double winningMultiplier) {
        this.winningMultiplier = winningMultiplier;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        this.maxBet = maxBet;
    }

    public int getMinBet() {
        return minBet;
    }

    public void setMinBet(int minBet) {
        this.minBet = minBet;
    }

/*    public static class Builder {
        private String name;
        private double chanceOfWinning;
        private double winningMultiplier;
        private int maxBet;
        private int minBet;

        public static Builder gameWith() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withChanceOfWinning(double chanceOfWinning) {
            this.chanceOfWinning = chanceOfWinning;

            return this;
        }

        public Builder withWinningMultiplier(double winningMultiplier) {
            this.winningMultiplier = winningMultiplier;

            return this;
        }

        public Builder withMaxBet(int maxBet) {
            this.maxBet = maxBet;

            return this;
        }

        public Builder withMinBet(int minBet) {
            this.minBet = minBet;

            return this;
        }
    }*/
}
