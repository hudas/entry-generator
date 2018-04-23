package org.ignas.datagenerator;

import org.ignas.datagenerator.repositories.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.ignas.datagenerator.TransactionProfile.BIG_FRAUD;
import static org.ignas.datagenerator.TransactionProfile.EXPECTED_FRAUD;
import static org.ignas.datagenerator.TransactionProfile.SMALL_FRAUD;

@Service
public class BehaviourGenerator {

    public static final double FRAUD_PROBABILITY = 0.01d;

    public static final Integer[] RISKY_HOURS = { 22, 2, 3, 4 };
    public static final String[] RISKY_CREDITOR = { "200", "201", "202", "203" };
    public static final String[] ALL_CREDITOR = { "100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "200", "201", "202", "203" };

    public static final Location[] RISKY_LOCATIONS = {
            new Location(50f, 22),
            new Location(60f, 28),
            new Location(52f, 20)
    };
    public static final Location[] ALL_LOCATIONS = {
            new Location(53f, 23),
            new Location(53f, 24),
            new Location(53f, 25),
            new Location(53f, 26),

            new Location(54f, 23),
            new Location(54f, 24),
            new Location(54f, 25),
            new Location(54f, 26),

            new Location(55f, 23),
            new Location(55f, 24),
            new Location(55f, 25),
            new Location(55f, 26),

            new Location(56f, 23),
            new Location(56f, 24),
            new Location(56f, 25),
            new Location(56f, 26),


            new Location(50f, 22),
            new Location(60f, 28),
            new Location(52f, 20)
    };


    @Autowired
    private TestCaseRepository repository;

    @Transactional
    public void generateDebtor(String debtor) {
        List<TestCase> newCases = new ArrayList<>();

        TestCase previousCase = null;
        LocalDateTime start = LocalDate.of(2008, 01, 01).atStartOfDay();

        while (true) {
            TransactionProfile profile = randomlySelectProfile();

            LocalDateTime previousTime = previousCase != null ? previousCase.getTime() : start;

            String creditor = null;
            LocalDateTime time = generateNewTime(profile, previousTime);

            double amount = Math.random() * (profile.getMaxAmount() - profile.getMinAmount()) + profile.getMinAmount();

            Location location = selectLocation(profile);

            TestCase testCase = new TestCase(
                    debtor,
                    selectCreditor(profile),
                    time,
                    BigDecimal.valueOf(amount),
                    BigDecimal.valueOf(location.getLatitude()),
                    BigDecimal.valueOf(location.getLongtitude()),
                    profile == BIG_FRAUD
                            || profile == EXPECTED_FRAUD
                            || profile == SMALL_FRAUD,
                    profile
            );

            if (time.isAfter(LocalDate.of(2018, 04, 25).atStartOfDay())) {
                break;
            }

            previousCase = testCase;
            newCases.add(testCase);
        }

        repository.saveAll(newCases);
    }

    private Location selectLocation(TransactionProfile profile) {
        switch (profile) {
            case BIG_FRAUD:
            case EXPECTED_FRAUD:
                int creditorIndex = Double.valueOf(Math.random() * (RISKY_LOCATIONS.length - 1)).intValue();
                float inc = Double.valueOf(Math.random() * 0.5f).floatValue();
                return RISKY_LOCATIONS[creditorIndex].with(inc, inc);
            case SMALL_FRAUD:
            case BIG_PURCHASE:
            case EXPECTED_PURCHASE:
            case SMALL_PURCHASE:
                creditorIndex = Double.valueOf(Math.random() * (ALL_LOCATIONS.length - 1)).intValue();
                inc = Double.valueOf(Math.random() * 0.5f).floatValue();
                return ALL_LOCATIONS[creditorIndex].with(inc, inc);
        }

        return new Location(54, 25);
    }

    private String selectCreditor(TransactionProfile profile) {
        switch (profile) {
            case BIG_FRAUD:
            case EXPECTED_FRAUD:
                int creditorIndex = Double.valueOf(Math.random() * (RISKY_CREDITOR.length - 1)).intValue();
                return RISKY_CREDITOR[creditorIndex];
            case SMALL_FRAUD:
            case BIG_PURCHASE:
            case EXPECTED_PURCHASE:
            case SMALL_PURCHASE:
                creditorIndex = Double.valueOf(Math.random() * (ALL_CREDITOR.length - 1)).intValue();
                return ALL_CREDITOR[creditorIndex];
        }

        return "100";
    }

    private LocalDateTime generateNewTime(TransactionProfile profile, LocalDateTime previousTime) {
        LocalDateTime time = null;

        switch (profile) {
            case BIG_FRAUD:
                time = bigFraudTime(previousTime);
                break;
            case EXPECTED_FRAUD:
                time = nextTimeByAddition(previousTime, 48);
                break;
            case SMALL_FRAUD:
                time = nextTimeByAddition(previousTime, 6);
                break;
            case BIG_PURCHASE:
                int daysToAdd = Double.valueOf(Math.random() * 5).intValue() + 5;
                int hoursToAdd = Double.valueOf(Math.random() * 12).intValue() + 9;
                int secondsToAdd = Double.valueOf(Math.random() * 3599).intValue();
                time = previousTime.plus(daysToAdd, ChronoUnit.DAYS).withHour(hoursToAdd).plus(secondsToAdd, ChronoUnit.SECONDS);
                break;
            case EXPECTED_PURCHASE:
                daysToAdd = Double.valueOf(Math.random() * 5).intValue() + 5;
                hoursToAdd = Double.valueOf(Math.random() * 12).intValue() + 9;
                secondsToAdd = Double.valueOf(Math.random() * 3599).intValue();
                time = previousTime.plus(daysToAdd, ChronoUnit.DAYS).withHour(hoursToAdd).plus(secondsToAdd, ChronoUnit.SECONDS);
                break;
            case SMALL_PURCHASE:
                daysToAdd = Double.valueOf(Math.random() * 2).intValue();
                hoursToAdd = Double.valueOf(Math.random() * 18).intValue() + 8;
                secondsToAdd = Double.valueOf(Math.random() * 3599).intValue();
                if (hoursToAdd > 23) {
                    daysToAdd++;
                    hoursToAdd -= 23;
                }
                if (daysToAdd == 0 && hoursToAdd <= previousTime.getHour()) {
                    time = previousTime.plus(1, ChronoUnit.DAYS).withHour(hoursToAdd).plus(secondsToAdd, ChronoUnit.SECONDS);
                } else {
                    time = previousTime.plus(daysToAdd, ChronoUnit.DAYS).withHour(hoursToAdd).plus(secondsToAdd, ChronoUnit.SECONDS);
                }
                break;
        }
        return time;
    }

    private LocalDateTime nextTimeByAddition(LocalDateTime previousTime, int i) {
        int hoursToAdd = Double.valueOf(Math.random() * i).intValue();
        int secondsToAdd = Double.valueOf(Math.random() * 3599).intValue();
        return previousTime.plus(hoursToAdd, ChronoUnit.HOURS).plus(secondsToAdd, ChronoUnit.SECONDS);
    }

    private LocalDateTime bigFraudTime(LocalDateTime previousTime) {
        LocalDateTime time;
        int selectedHourIndex = Double.valueOf(Math.random() * (RISKY_HOURS.length - 1)).intValue();
        Integer hour = RISKY_HOURS[selectedHourIndex];
        int secondsAfter = Double.valueOf(Math.random() * 3599).intValue();
        time = previousTime.plusDays(1).withHour(hour).plus(secondsAfter, ChronoUnit.SECONDS);
        return time;
    }


    private TransactionProfile randomlySelectProfile() {
        boolean fraud = generateRandomEvent(FRAUD_PROBABILITY);

        if (fraud) {
            return selectFraudProfile();
        } else {
            return selectPurchaseProfile();
        }
    }

    private TransactionProfile selectPurchaseProfile() {
        double random = Math.random(); // Generates percentage.

        double leftover = random;

        if (leftover <= TransactionProfile.BIG_PURCHASE.getProbability()) {
            return TransactionProfile.BIG_PURCHASE;
        }

        leftover -= TransactionProfile.BIG_PURCHASE.getProbability();

        if (leftover <= TransactionProfile.EXPECTED_PURCHASE.getProbability()) {
            return TransactionProfile.EXPECTED_PURCHASE;
        }

        return TransactionProfile.SMALL_PURCHASE;
    }

    private TransactionProfile selectFraudProfile() {
        double random = Math.random(); // Generates percentage.

        double leftover = random;

        if (leftover <= BIG_FRAUD.getProbability()) {
            return BIG_FRAUD;
        }

        leftover -= BIG_FRAUD.getProbability();

        if (leftover <= EXPECTED_FRAUD.getProbability()) {
            return EXPECTED_FRAUD;
        }

        return SMALL_FRAUD;
    }

    private boolean generateRandomEvent(double fraudProbability) {
        double random = Math.random(); // Generates percentage.

        return (random <= fraudProbability);
    }
}
