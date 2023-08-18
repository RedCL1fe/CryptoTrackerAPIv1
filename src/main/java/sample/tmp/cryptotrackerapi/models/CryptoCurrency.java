package sample.tmp.cryptotrackerapi.models;

import jakarta.persistence.*;

import javax.validation.constraints.Size;

@Entity
@Table(name = "crypto_currency")
public class CryptoCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    @Size(min = 3, max = 10)
    private String symbol;

    @Column(name = "current_price")
    private Double currentPrice;

    public CryptoCurrency() {
    }

    public CryptoCurrency(String name, String symbol, Double currentPrice) {
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
