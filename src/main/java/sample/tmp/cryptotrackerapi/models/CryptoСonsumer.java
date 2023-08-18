package sample.tmp.cryptotrackerapi.models;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Entity
@Table(name = "consumer")
public class CryptoСonsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @Size(min = 2, max = 100, message =
            "Your nickname must be no shorter than 2 characters and no longer than 100")
    private String username;

    @Email
    @Column(name = "email")
    @Max(100)
    private String email;

    @Size(min = 8, max = 32, message =
            "The password must be no shorter than 8 characters and no longer than 32")
    @Column(name = "password")
    private String password;

    public CryptoСonsumer() {
    }

    public CryptoСonsumer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public CryptoСonsumer(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}