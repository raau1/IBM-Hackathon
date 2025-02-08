package com.financeadvicechatbot.model;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    // Attributes
    @Id
    private String email;
    private String password;
    private String financialAim;
    private float monthlyIncome;
    private float monthlySavings;
    private float spendingNeccesities;
    private float spendingLeisure;
    private float spendingSubscriptions;
    private float spendingOther;

    // Getters and Setters
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

    public String getFinancialAim() {
        return financialAim;
    }

    public void setFinancialAim(String financialAim) {
        this.financialAim = financialAim;
    }

    public float getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(float monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public float getMonthlySavings() {
        return monthlySavings;
    }

    public void setMonthlySavings(float monthlySavings) {
        this.monthlySavings = monthlySavings;
    }

    public float getSpendingNeccesities() {
        return spendingNeccesities;
    }

    public void setSpendingNeccesities(float spendingNeccesities) {
        this.spendingNeccesities = spendingNeccesities;
    }

    public float getSpendingLeisure() {
        return spendingLeisure;
    }

    public void setSpendingLeisure(float spendingLeisure) {
        this.spendingLeisure = spendingLeisure;
    }

    public float getSpendingSubscriptions() {
        return spendingSubscriptions;
    }

    public void setSpendingSubscriptions(float spendingSubscriptions) {
        this.spendingSubscriptions = spendingSubscriptions;
    }

    public float getSpendingOther() {
        return spendingOther;
    }

    public void setSpendingOther(float spendingOther) {
        this.spendingOther = spendingOther;
    }
}
