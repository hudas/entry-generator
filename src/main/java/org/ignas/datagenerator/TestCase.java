package org.ignas.datagenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_cases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String debtor;

    private String creditor;

    private LocalDateTime time;

    private BigDecimal amount;

    private BigDecimal locationLatitude;

    private BigDecimal locationLongtitude;

    private boolean fraud;

    private boolean processed;

    @Enumerated(EnumType.STRING)
    private TransactionProfile profile;


    public TestCase(
            String debtor,
            String creditor,
            LocalDateTime time,
            BigDecimal amount,
            BigDecimal locationLatitude,
            BigDecimal locationLongtitude,
            boolean fraud,
            TransactionProfile profile) {

        this.debtor = debtor;
        this.creditor = creditor;
        this.time = time;
        this.amount = amount;
        this.locationLatitude = locationLatitude;
        this.locationLongtitude = locationLongtitude;
        this.fraud = fraud;
        this.profile = profile;
    }

    public TestCase() {
    }

    public Long getId() {
        return id;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getLocationLatitude() {
        return locationLatitude;
    }

    public BigDecimal getLocationLongtitude() {
        return locationLongtitude;
    }

    public boolean isFraud() {
        return fraud;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void markProcessed() {
        this.processed = true;
    }
}
