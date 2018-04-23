package org.ignas.datagenerator.dto;

public class EvaluationDTO {

    private String transactionId;
    private String debtorCreditCardId;
    private String debtorAccountId;
    private String creditorAccountId;
    private Float amount;
    private String time;
    private LocationDTO location;

    public EvaluationDTO(
            String transactionId,
            String debtorCreditCardId,
            String debtorAccountId,
            String creditorAccountId,
            Float amount,
            String time,
            LocationDTO location) {

        this.transactionId = transactionId;
        this.debtorCreditCardId = debtorCreditCardId;
        this.debtorAccountId = debtorAccountId;
        this.creditorAccountId = creditorAccountId;
        this.amount = amount;
        this.time = time;
        this.location = location;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getDebtorCreditCardId() {
        return debtorCreditCardId;
    }

    public String getDebtorAccountId() {
        return debtorAccountId;
    }

    public String getCreditorAccountId() {
        return creditorAccountId;
    }

    public Float getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public LocationDTO getLocation() {
        return location;
    }
}
