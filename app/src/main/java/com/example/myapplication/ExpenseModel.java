package com.example.myapplication;

import java.time.LocalDate;
import java.util.Date;

public class ExpenseModel {
    private int id;
    private double expense;
    private String expenseType;
    private LocalDate expenseDate;
    private boolean recurring;

    public ExpenseModel(int id, double expense, String expenseType, LocalDate expenseDate, boolean recurring) {
        this.id = id;
        this.expense = expense;
        this.expenseType = expenseType;
        this.expenseDate = expenseDate;
        this.recurring = recurring;
    }

    public ExpenseModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "id=" + id +
                ", expense=" + expense +
                ", expenseType='" + expenseType + '\'' +
                ", expenseDate=" + expenseDate +
                ", recurring=" + recurring +
                '}';
    }
}
