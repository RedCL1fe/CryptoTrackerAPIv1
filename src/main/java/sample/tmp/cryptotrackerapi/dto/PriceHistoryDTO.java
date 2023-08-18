package sample.tmp.cryptotrackerapi.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PriceHistoryDTO {

    private Long id;
    private String cryptoCurrencySymbol;
    private Date date;
    private BigDecimal price;

    public PriceHistoryDTO() {
        this.id = id;
        this.cryptoCurrencySymbol = cryptoCurrencySymbol;
        this.date = date;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCryptoCurrencySymbol() {
        return cryptoCurrencySymbol;
    }

    public void setCryptoCurrencySymbol(String cryptoCurrencySymbol) {
        this.cryptoCurrencySymbol = cryptoCurrencySymbol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
