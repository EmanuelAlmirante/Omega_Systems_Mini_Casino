package model;

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
    private Date birthDate;

    private double balance;

    public Player() {
    }

    public Player(long id, String name, String username, Date birthDate, double balance) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.birthDate = birthDate;
        this.balance = balance;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
