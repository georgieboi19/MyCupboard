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

import static com.w1667474.mycupboard.details.col5;
import static com.w1667474.mycupboard.details.tableName;
import static com.w1667474.mycupboard.details.col1;

public class displayProducts extends AppCompatActivity {

    DBHandler products;
    String ORDER_BY = col1;
    LinearLayout lineardisplay;
    List<String> items = new ArrayList<>();
    List<String> toKitchin = new ArrayList<>();
    int itemId;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_products);
        products = new DBHandler(this);
        Cursor cursor = returnItems();
        showItems(cursor);

    }

    private Cursor returnItems(){
        SQLiteDatabase db = products.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] {"_ID", col1}, null, null, null, null, ORDER_BY);
        return cursor;
    }

    private void showItems(Cursor cursor) {
        lineardisplay = findViewById(R.id.linear_display);

        while (cursor.moveToNext()) {
            String prodName = cursor.getString(1);
            //adds items to items array
            items.add(prodName);
        }
        //goes thrugh items array and adds a checkbox with the value of i of items then adds to linear layout
        for (int i = 0; i < items.size(); i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            itemId = i;
            String temp = items.get(i);
            cb.setText(temp);
            cb.setId(i);
            Log.i("id is", Integer.toString(itemId));
            lineardisplay.addView(cb);
        }
        cursor.close();
        products.close();
    }
//on clicking button it checks to see which checkbox is ticked and adds the checkbox value to tokitchin array
    public void addToKitchen (View view){
        SQLiteDatabase db = products.getReadableDatabase();
        for (int i= 0; i<items.size(); i++){
            String temp = Integer.toString(i);
            check = findViewById(getResources().getIdentifier(temp,"id", getPackageName()));
            Log.i("id is", temp);
            if (check.isChecked()){
                toKitchin.add(check.getText().toString());
                Log.i("added to Kitchen:", temp);
            }
        }
        //goes through tokitchin and updates the value of i in the table to 1 (availible)
        for(int j = 0; j<toKitchin.size();j++){

            String[] temp = {toKitchin.get(j)};
            ContentValues cV = new ContentValues();
            cV.put(col5, 1);
            db.update(tableName,cV, "prodName=?",temp);
            Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
        }
    }
}
