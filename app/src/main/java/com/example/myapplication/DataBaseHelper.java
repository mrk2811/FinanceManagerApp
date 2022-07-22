package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.data.BarEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_EXPENSE_AMOUNT = "EXPENSE_AMOUNT";
    public static final String COLUMN_EXPENSE_TYPE = "EXPENSE_TYPE";
    public static final String COLUMN_EXPENSE_DATE = "EXPENSE_DATE";
    public static final String COLUMN_RECURRING = "RECURRING";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "expense.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EXPENSE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EXPENSE_AMOUNT + " DOUBLE, " + COLUMN_EXPENSE_TYPE + " TEXT, " + COLUMN_EXPENSE_DATE + " TEXT, " + COLUMN_RECURRING + " BOOL)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ExpenseModel expenseModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EXPENSE_AMOUNT, expenseModel.getExpense());
        cv.put(COLUMN_EXPENSE_TYPE, expenseModel.getExpenseType());
        cv.put(COLUMN_EXPENSE_DATE, expenseModel.getExpenseDate().toString());
        cv.put(COLUMN_RECURRING, expenseModel.isRecurring());

        long insert = db.insert(EXPENSE_TABLE, null, cv);
        return insert != -1;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<ExpenseModel> getExpenseList() {
       ArrayList<ExpenseModel> expenseList = new ArrayList<>();
       String queryString = "SELECT * FROM " + EXPENSE_TABLE;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = db.rawQuery(queryString, null);

       if (cursor.moveToFirst()) {
           do {
               int expenseId = cursor.getInt(0);
               double expenseAmount = cursor.getDouble(1);
               String expenseType = cursor.getString(2);
               String expenseDate = cursor.getString(3);
               boolean expenseRecurring = cursor.getInt(4) == 1 ? true: false;

               LocalDate newDate = LocalDate.parse(expenseDate);
               ExpenseModel newExpense = new ExpenseModel(expenseId, expenseAmount,
                       expenseType, newDate, expenseRecurring);
               expenseList.add(newExpense);

           } while(cursor.moveToNext());
       } else {

       }
       cursor.close();
       db.close();
       return expenseList;
    }
}
