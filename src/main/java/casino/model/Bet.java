package casino.model;

public class Bet {
    private long gameId;
    private String username;
    private double betValue;

    public Bet() {
    }

    public Bet(Builder builder) {
        this.gameId = builder.gameId;
        this.username = builder.username;
        this.betValue = builder.betValue;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getBetValue() {
        return betValue;
    }

    public void setBetValue(double betValue) {
        this.betValue = betValue;
    }

    public static class Builder {
        private long gameId;
        private String username;
        private double betValue;

        public static Builder betWith() {
            return new Builder();
        }

        public Builder withGameId(long gameId) {
            this.gameId = gameId;

            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;

            return this;
        }

        public Builder withBetValue(double betValue) {
            this.betValue = betValue;

            return this;
        }

        public Bet build() {
            return new Bet(this);
        }
    }
}
