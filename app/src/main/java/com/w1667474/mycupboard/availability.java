package com.w1667474.mycupboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.col5;
import static com.w1667474.mycupboard.details.tableName;

public class availability extends AppCompatActivity {

    DBHandler products;
    List<String> items = new ArrayList<>();
    List<String> fromKitchin = new ArrayList<>();
    LinearLayout lineardisplay;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        products = new DBHandler(this);
        Cursor cursor = returnItems();
        showItems(cursor);
    }
    private Cursor returnItems(){
        //sets the new SQLITEDB to readable
        SQLiteDatabase db = products.getReadableDatabase();
        //adds the query to a cursor (which stores the result)
        //query looks in the table items and gets column1 where prodavail  is the contents of the string array where value is 1 (avaible)
        Cursor cursor = db.query(tableName, new String[] {col1}, "prodAvail=?", new String[]{String.valueOf(1)}, null, null, null);
        return cursor;
    }
//this takes the variable of cursor
    private void showItems(Cursor cursor) {
        //gets the linear layout as a variable
        lineardisplay = findViewById(R.id.linear_display);
//itterates through the cursor until there is nothing left
        while (cursor.moveToNext()) {
            //gets the cursor at col1 and saves into string
            String prodName = cursor.getString(0);
            //adds the string to the arraylist items
            items.add(prodName);
        }
        //goes through items list
        for (int i = 0; i < items.size(); i++) {
            //creates a new checkbox, gives it the value at i of items, sets as checked and adds this to the linear layout of scrolview
            CheckBox cb = new CheckBox(getApplicationContext());
            String temp = items.get(i);
            cb.setText(temp);
            cb.setId(i);
            cb.setChecked(true);
            lineardisplay.addView(cb);
        }
        cursor.close();
        products.close();
    }
//onclick this method is run
    public void updateAvailablity (View view){
        SQLiteDatabase db = products.getReadableDatabase();
        for (int i= 0; i<items.size(); i++){
            String temp = Integer.toString(i);
            check = findViewById(getResources().getIdentifier(temp,"id", getPackageName()));
            Log.i("id is", temp);
            if (!check.isChecked()){
                //if not check add the value of checkbox to fromkitchin array
                fromKitchin.add(check.getText().toString());
                Log.i("added to Kitchen:", temp);
            }
        }
        for(int j = 0; j<fromKitchin.size();j++){
//goes through the fromkitchen array and updates the items to have col5 as 0 (not availible)
            String[] temp = {fromKitchin.get(j)};
            ContentValues cV = new ContentValues();
            cV.put(col5, 0);
            db.update(tableName,cV, "prodName=?",temp);
            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
        }
    }
}
