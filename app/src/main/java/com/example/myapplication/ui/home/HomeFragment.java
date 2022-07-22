package com.example.myapplication.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.ExpenseModelData;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private BarChart barChart;
    private PieChart pieChart;
    private Button weekly_button, monthly_button, yearly_button;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //----------------------------------------------------------------------
        weekly_button = root.findViewById(R.id.weekly_button);
        monthly_button = root.findViewById(R.id.monthly_button);
        yearly_button = root.findViewById(R.id.yearly_button);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
        ExpenseModelData expenseModelData = new ExpenseModelData(dataBaseHelper.getExpenseList());
        expenseModelData.setDataForWeeklyExpense();

        barChart = root.findViewById(R.id.barChart);
        expenseModelData.generateBarChart(barChart, expenseModelData.getExpenseBarEntries(), getActivity());

         weekly_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseModelData.setDataForWeeklyExpense();
                expenseModelData.generateBarChart(barChart, expenseModelData.getExpenseBarEntries(), getActivity());
            }
        });

        monthly_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseModelData.setDataForMonthlyExpense();
                expenseModelData.generateBarChart(barChart, expenseModelData.getExpenseBarEntries(), getActivity());
            }
        });

        yearly_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseModelData.setDataForAnnualExpense();
                expenseModelData.generateBarChart(barChart, expenseModelData.getExpenseBarEntries(), getActivity());
            }
        });


        pieChart = root.findViewById(R.id.pie_chart);
        PieDataSet pieDataSet = new PieDataSet(expenseModelData.getExpensePieEntries(), "");
        //pieDataSet.setColor(getResources().getColor(R.color.purple_700));
        int[] colors = new  int[] {
                R.color.crimson, R.color.orangered, R.color.gold, R.color.forestgreen,
                R.color.saddlebrown, R.color.magenta, R.color.mediumblue
        };
        pieDataSet.setColors(colors, getContext());
        pieDataSet.setValueTextColor(getResources().getColor(R.color.black));
        pieDataSet.setValueTextSize(16f);
        pieChart.setDrawEntryLabels(false);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setTouchEnabled(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Expense Types");
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);

        //--------------------------------------------------------------------------
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}