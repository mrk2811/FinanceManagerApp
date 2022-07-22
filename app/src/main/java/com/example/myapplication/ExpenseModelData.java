package com.example.myapplication;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExpenseModelData {
    private ArrayList<ExpenseModel> expenseList;
    private ArrayList<BarEntry> expenseBarEntries;
    private String[] expenseDates;

    public ExpenseModelData(ArrayList<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }

    public ArrayList<BarEntry> getExpenseBarEntries() {
        return expenseBarEntries;
    }

    public String[] getExpenseDates() {
        return expenseDates;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDataForWeeklyExpense() {

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate twoDaysAgo = yesterday.minusDays(1);
        LocalDate threeDaysAgo = twoDaysAgo.minusDays(1);
        LocalDate fourDaysAgo = threeDaysAgo.minusDays(1);
        LocalDate fiveDaysAgo = fourDaysAgo.minusDays(1);
        LocalDate sixDaysAgo = fiveDaysAgo.minusDays(1);

        expenseDates = null;
        expenseDates = new String[] {abbreviate(sixDaysAgo),
                abbreviate(fiveDaysAgo), abbreviate(fourDaysAgo),
                abbreviate(threeDaysAgo), abbreviate(twoDaysAgo),
                abbreviate(yesterday), abbreviate(today) };


        double todayAmount = 0;
        double yesterdayAmount = 0;
        double twoDaysAgoAmount = 0;
        double threeDaysAgoAmount = 0;
        double fourDaysAgoAmount = 0;
        double fiveDaysAgoAmount = 0;
        double sixDaysAgoAmount = 0;

        for (ExpenseModel expense: expenseList) {
            double amount = expense.getExpense();
            LocalDate currDate = expense.getExpenseDate();
           if (currDate.equals(today)) {
               todayAmount += amount;
           } else if(currDate.equals(yesterday)) {
               yesterdayAmount += amount;
           } else if(currDate.equals(twoDaysAgo)) {
               twoDaysAgoAmount += amount;
           } else if(currDate.equals(threeDaysAgo)) {
               threeDaysAgoAmount += amount;
           } else if(currDate.equals(fourDaysAgo)) {
               fourDaysAgoAmount += amount;
           } else if(currDate.equals(fiveDaysAgo)) {
               fiveDaysAgoAmount += amount;
           } else if(currDate.equals(sixDaysAgo)) {
               sixDaysAgoAmount += amount;
           }
        }

        expenseBarEntries = null;
        expenseBarEntries = new ArrayList<>();
        expenseBarEntries.add(new BarEntry(6, (float)todayAmount));
        expenseBarEntries.add(new BarEntry(5, (float)yesterdayAmount));
        expenseBarEntries.add(new BarEntry(4, (float)twoDaysAgoAmount));
        expenseBarEntries.add(new BarEntry(3, (float)threeDaysAgoAmount));
        expenseBarEntries.add(new BarEntry(2, (float)fourDaysAgoAmount));
        expenseBarEntries.add(new BarEntry(1, (float)fiveDaysAgoAmount));
        expenseBarEntries.add(new BarEntry(0, (float)sixDaysAgoAmount));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDataForMonthlyExpense() {
        expenseDates = null;
        expenseDates = new String[] {"Week 1", "Week 2", "Week 3", "Week 4"};

        LocalDate thisWeekEnd = LocalDate.now();
        LocalDate thisWeekStart = thisWeekEnd.minusWeeks(1);
        LocalDate prevWeekEnd = thisWeekStart.minusDays(1);
        LocalDate prevWeekStart = prevWeekEnd.minusWeeks(1);
        LocalDate twoWeeksAgoEnd = prevWeekStart.minusDays(1);
        LocalDate twoWeeksAgoStart = twoWeeksAgoEnd.minusWeeks(1);
        LocalDate threeWeeksAgoEnd = twoWeeksAgoStart.minusDays(1);
        LocalDate threeWeeksAgoStart = threeWeeksAgoEnd.minusWeeks(1);

        double thisWeekAmount = 0;
        double prevWeekAmount = 0;
        double twoWeeksAgoAmount = 0;
        double threeWeeksAgoAmount = 0;

        for (ExpenseModel expense: expenseList) {
            double amount = expense.getExpense();
            LocalDate currDate = expense.getExpenseDate();

            if(currDate.isBefore(thisWeekEnd.plusDays(1)) && currDate.isAfter(prevWeekEnd)) { //current week (Week 4)
                thisWeekAmount += amount;
            } else if(currDate.isBefore(thisWeekStart) && currDate.isAfter(twoWeeksAgoEnd)) { // Week 3
                prevWeekAmount += amount;
            } else if(currDate.isBefore(prevWeekStart) && currDate.isAfter(threeWeeksAgoEnd)) { // Week 2
                twoWeeksAgoAmount += amount;
            } else if(currDate.isBefore(twoWeeksAgoStart) && currDate.isAfter(threeWeeksAgoStart.minusDays(1))) { // Week 1
                threeWeeksAgoAmount += amount;
            }
        }

        expenseBarEntries = null;
        expenseBarEntries = new ArrayList<>();
        expenseBarEntries.add(new BarEntry(3, (float)thisWeekAmount));
        expenseBarEntries.add(new BarEntry(2, (float)prevWeekAmount));
        expenseBarEntries.add(new BarEntry(1, (float)twoWeeksAgoAmount));
        expenseBarEntries.add(new BarEntry(0, (float)threeWeeksAgoAmount));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDataForAnnualExpense() {
        expenseDates = null;
        expenseDates = new String[] {"Q 1", "Q 2", "Q 3", "Q 4"};

        LocalDate thisQuarterEnd = LocalDate.now();
        LocalDate thisQuarterStart = thisQuarterEnd.minusMonths(3);
        LocalDate prevQuarterEnd = thisQuarterStart.minusDays(1);
        LocalDate prevQuarterStart = prevQuarterEnd.minusMonths(3);
        LocalDate twoQuartersAgoEnd = prevQuarterStart.minusDays(1);
        LocalDate twoQuartersAgoStart = twoQuartersAgoEnd.minusMonths(3);
        LocalDate threeQuartersAgoEnd = twoQuartersAgoStart.minusDays(1);
        LocalDate threeQuartersAgoStart = threeQuartersAgoEnd.minusMonths(3);

        double thisQuarterAmount = 0;
        double prevQuarterAmount = 0;
        double twoQuartersAgoAmount = 0;
        double threeQuartersAgoAmount = 0;

        for (ExpenseModel expense: expenseList) {
            double amount = expense.getExpense();
            LocalDate currDate = expense.getExpenseDate();

            if(currDate.isBefore(thisQuarterEnd.plusDays(1)) && currDate.isAfter(prevQuarterEnd)) { //current week (Week 4)
                thisQuarterAmount += amount;
            } else if(currDate.isBefore(thisQuarterStart) && currDate.isAfter(twoQuartersAgoEnd)) { // Week 3
                prevQuarterAmount += amount;
            } else if(currDate.isBefore(prevQuarterStart) && currDate.isAfter(threeQuartersAgoEnd)) { // Week 2
                twoQuartersAgoAmount += amount;
            } else if(currDate.isBefore(twoQuartersAgoStart) && currDate.isAfter(threeQuartersAgoStart.minusDays(1))) { // Week 1
                threeQuartersAgoAmount += amount;
            }
        }

        expenseBarEntries = null;
        expenseBarEntries = new ArrayList<>();
        expenseBarEntries.add(new BarEntry(3, (float)thisQuarterAmount));
        expenseBarEntries.add(new BarEntry(2, (float)prevQuarterAmount));
        expenseBarEntries.add(new BarEntry(1, (float)twoQuartersAgoAmount));
        expenseBarEntries.add(new BarEntry(0, (float)threeQuartersAgoAmount));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String abbreviate(LocalDate date) {

        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMM d"));
        return formattedDate;
    }

    public void generateBarChart(BarChart barChart, ArrayList<BarEntry> currExpenseBarEntries, Context context) {

        barChart.clear();
        BarDataSet barDataSet = new BarDataSet(currExpenseBarEntries, "Expenses($)");
        barDataSet.setValueTextColor(context.getResources().getColor(R.color.black));
        barDataSet.setColor(context.getResources().getColor(R.color.design_default_color_primary_dark));
        barDataSet.setValueTextSize(16f);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.getDescription().setEnabled(false);
        barChart.notifyDataSetChanged();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(expenseDates));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    public ArrayList<PieEntry> getExpensePieEntries() {
        int GroceryAmount = 0;
        int ShoppingAmount = 0;
        int LeisureAmount = 0;
        int FoodAmount = 0;
        int HealthAmount = 0;
        int OtherAmount = 0;

        for (ExpenseModel expense: expenseList) {
            double amount = expense.getExpense();
            String type = expense.getExpenseType();

            if(type.equals("Grocery")) {
                GroceryAmount += amount;
            } else if (type.equals("Shopping")) {
                ShoppingAmount += amount;
            } else if (type.equals("Leisure")) {
                LeisureAmount += amount;
            } else if (type.equals("Food")) {
                FoodAmount += amount;
            } else if (type.equals("Health")) {
                HealthAmount += amount;
            } else if (type.equals("Other")) {
                OtherAmount += amount;
            }
        }

        ArrayList<PieEntry> pieValues = new ArrayList<>();
        if (GroceryAmount != 0)
            pieValues.add(new PieEntry(GroceryAmount, "Grocery"));
        if (ShoppingAmount != 0)
            pieValues.add(new PieEntry(ShoppingAmount, "Shopping"));
        if (LeisureAmount != 0)
            pieValues.add(new PieEntry(LeisureAmount, "Leisure"));
        if (FoodAmount != 0)
            pieValues.add(new PieEntry(FoodAmount, "Food"));
        if (HealthAmount != 0)
            pieValues.add(new PieEntry(HealthAmount, "Health"));
        if (OtherAmount != 0)
            pieValues.add(new PieEntry(OtherAmount, "Other"));

        return pieValues;
    }

}
