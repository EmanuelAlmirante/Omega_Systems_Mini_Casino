package model;

import javax.persistence.*;
import java.math.BigDecimal;

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
        if (0 > chanceOfWinning && chanceOfWinning > 1 ) {
            throw new IllegalArgumentException("The chance of winning must be between 0 and 1!");
        } if (BigDecimal.valueOf(chanceOfWinning).scale() != 2) {
            throw new IllegalArgumentException("The chance of winning must have two decimal!");
        } else {
            this.chanceOfWinning = chanceOfWinning;
        }
    }

    public double getWinningMultiplier() {
        return winningMultiplier;
    }

    public void setWinningMultiplier(double winningMultiplier) {
        if (winningMultiplier < 0) {
            throw new IllegalArgumentException("The winning multiplier must be bigger than 0!");
        } else {
            this.winningMultiplier = winningMultiplier;
        }
    }

    public int getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(int maxBet) {
        if (maxBet < 0) {
            throw new IllegalArgumentException("The max bet must be bigger than 0!");
        } if (maxBet< minBet) {
            throw new IllegalArgumentException("The max bet must be bigger than the min bet!");
        }
        else {
            this.maxBet = maxBet;
        }
    }

    public int getMinBet() {
        return minBet;
    }

    public void setMinBet(int minBet) {
        if (minBet < 0) {
            throw new IllegalArgumentException("The min bet must be bigger than 0!");
        } if (minBet > maxBet) {
            throw new IllegalArgumentException("The min bet must be smaller than the max bet!");
        } else {
            this.minBet = minBet;
        }
    }
}
