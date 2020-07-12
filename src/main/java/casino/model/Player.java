package casino.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "players")
@IdClass(CompositeKey.class)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Id
    private String username;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

    private double balance = 0;

    public Player() {
    }

    public Player(Builder builder) {
        this.name = builder.name;
        this.username = builder.username;
        this.birthdate = builder.birthdate;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static class Builder {
        private String name;
        private String username;
        private Date birthdate;

        public static Builder playerWith() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;

            return this;
        }

        public Builder withBirthdate(Date birthdate) {
            this.birthdate = birthdate;

            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
