package casino.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private BigDecimal chanceOfWinning;

    private double winningMultiplier;

    private double maxBet;

    private double minBet;

    public Game() {
    }

    public Game(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.chanceOfWinning = builder.chanceOfWinning;
        this.winningMultiplier = builder.winningMultiplier;
        this.maxBet = builder.maxBet;
        this.minBet = builder.minBet;
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

    public BigDecimal getChanceOfWinning() {
        return chanceOfWinning;
    }

    public void setChanceOfWinning(BigDecimal chanceOfWinning) {
        this.chanceOfWinning = chanceOfWinning;
    }

    public double getWinningMultiplier() {
        return winningMultiplier;
    }

    public void setWinningMultiplier(double winningMultiplier) {
        this.winningMultiplier = winningMultiplier;
    }

    public double getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(double maxBet) {
        this.maxBet = maxBet;
    }

    public double getMinBet() {
        return minBet;
    }

    public void setMinBet(double minBet) {
        this.minBet = minBet;
    }

    public static class Builder {
        private long id;
        private String name;
        private BigDecimal chanceOfWinning;
        private double winningMultiplier;
        private double maxBet;
        private double minBet;

        public static Builder gameWith() {
            return new Builder();
        }

        public Builder withId(Long id) {
            this.id = id;

            return this;
        }

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withChanceOfWinning(BigDecimal chanceOfWinning) {
            this.chanceOfWinning = chanceOfWinning;

            return this;
        }

        public Builder withWinningMultiplier(double winningMultiplier) {
            this.winningMultiplier = winningMultiplier;

            return this;
        }

        public Builder withMaxBet(double maxBet) {
            this.maxBet = maxBet;

            return this;
        }

        public Builder withMinBet(double minBet) {
            this.minBet = minBet;

            return this;
        }

        public Game build() {
            return new Game(this);
        }
    }
}
