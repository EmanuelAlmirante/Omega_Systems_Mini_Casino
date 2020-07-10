package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
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

    @JsonIgnore
    private double balance = 0;

    public Player() {
    }

    public Player(long id, String name, String username, Date birthDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.birthDate = birthDate;
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
        LocalDate today = LocalDate.now();
        LocalDate birthday = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (Period.between(birthday, today).getYears() < 18) {
            throw new IllegalArgumentException("Player must be older than 18!");
        } else {
            this.birthDate = birthDate;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
