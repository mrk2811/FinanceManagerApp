package com.example.myapplication.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.DataBaseHelper;
import com.example.myapplication.ExpenseModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import java.time.LocalDate;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private Button add_button;
    private EditText expense_amount_text;
    private RadioGroup expense_type_radio_group;
    private RadioButton expense_type_radio_button;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //-----------------------------------------------------------------------
        add_button = root.findViewById(R.id.add_button);
        expense_amount_text = root.findViewById(R.id.expense_amount_text);
        expense_type_radio_group = root.findViewById(R.id.expense_type_radio_group);

        add_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int radioId = expense_type_radio_group.getCheckedRadioButtonId();
                expense_type_radio_button = root.findViewById(radioId);

                ExpenseModel expenseModel;
                try{
                    expenseModel = new ExpenseModel(-1, Double.parseDouble(expense_amount_text.getText().
                            toString()), expense_type_radio_button.getText().toString(), LocalDate.now(), false);
                    Toast.makeText(getActivity(), expenseModel.toString(), Toast.LENGTH_SHORT).show();

                } catch(Exception e) {
                    Toast.makeText(getActivity(), "Error adding expense", Toast.LENGTH_SHORT).show();
                    expenseModel = new ExpenseModel(-1, 0, "Other", LocalDate.now(), false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                boolean success = dataBaseHelper.addOne(expenseModel);
                Toast.makeText(getActivity(), "Success = " + success, Toast.LENGTH_SHORT).show();


            }
        });

        //-----------------------------------------------------------------------
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}