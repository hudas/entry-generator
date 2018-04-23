package org.ignas.datagenerator;

import org.springframework.data.domain.Range;

public enum TransactionProfile {

    BIG_FRAUD(0.9f, 250, 1000),
    EXPECTED_FRAUD(0.05f, 50, 100),
    SMALL_FRAUD(0.05f, 5, 10),

    SMALL_PURCHASE(0.15f, 5, 15),
    EXPECTED_PURCHASE(0.8f, 20, 40),
    BIG_PURCHASE(0.05f, 100, 500);

    private float probability;

    private float minAmount;
    private float maxAmount;

    private float probabilityOfRiskyCreditor;
    private float probabilityOfRiskyTime;
    private float probabilityOfRiskyPlace;

    TransactionProfile(
            float probability,
            float minAmount, float maxAmount
//            ,
//            float probabilityOfRiskyCreditor, float probabilityOfRiskyTime, float probabilityOfRiskyPlace
    ) {
        this.probability = probability;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;

        this.probabilityOfRiskyCreditor = probabilityOfRiskyCreditor;
        this.probabilityOfRiskyTime = probabilityOfRiskyTime;
        this.probabilityOfRiskyPlace = probabilityOfRiskyPlace;
    }

    public float getProbability() {
        return probability;
    }

    public float getMinAmount() {
        return minAmount;
    }

    public float getMaxAmount() {
        return maxAmount;
    }
}
