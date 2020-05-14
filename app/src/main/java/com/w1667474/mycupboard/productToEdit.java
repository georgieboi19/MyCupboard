package com.w1667474.mycupboard;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.col2;
import static com.w1667474.mycupboard.details.col3;
import static com.w1667474.mycupboard.details.col4;
import static com.w1667474.mycupboard.details.col5;
import static com.w1667474.mycupboard.details.tableName;

public class productToEdit extends AppCompatActivity {

    DBHandler products;
    String newId;
    EditText nameText, weightText, priceText, descText;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_to_edit);
        Intent intent = getIntent();
        //gets the variable passed from previous activity
        newId = intent.getStringExtra("id");
        products = new DBHandler(this);
        Cursor cursor = returnItems();
        showItems(cursor);
    }
    //queries db where the prodname is the passed name from the previous activity
    private Cursor returnItems(){
        SQLiteDatabase db = products.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] {col1, col2, col3, col4, col5}, "prodName= ?", new String[] {newId}, null, null, null);
        return cursor;
    }
//reads through the returns db query and sets the variables at edit text to the columns in the cusor
    private void showItems(Cursor cursor) {

        while (cursor.moveToNext()) {
            String prodName = cursor.getString(0);
            String prodWeight = cursor.getString(1);
            String prodPrice = cursor.getString(2);
            String prodDescription = cursor.getString(3);
            int avail = cursor.getInt(4);
            nameText = findViewById(R.id.productName);
            weightText = findViewById(R.id.productWeight);
            priceText = findViewById(R.id.productPrice);
            descText = findViewById(R.id.editText);
            check = findViewById(R.id.checkBox);

            nameText.setText(prodName);
            weightText.setText(prodWeight);
            priceText.setText(prodPrice);
            Log.i("Desc", prodDescription);
            descText.setText(prodDescription);
            //depdning on col5s value it will set the checkbox as checked or not
            if (avail == 1){
                check.setChecked(true);
            } else{
                check.setChecked(false);
            }
        }
        cursor.close();
        products.close();
    }
//gets the fields as a variable first
    public void updateProduct (View view){
        int availValue;
        SQLiteDatabase db = products.getReadableDatabase();

        nameText = findViewById(R.id.productName);
        weightText = findViewById(R.id.productWeight);
        priceText = findViewById(R.id.productPrice);
        descText = findViewById(R.id.productDesc);
        check = findViewById(R.id.checkBox);

        String weightString = weightText.getText().toString();
        String priceString = priceText.getText().toString();


        String name = nameText.getText().toString();
        String desc = descText.getText().toString();

        if (check.isChecked()){
            availValue = 1;
        }else{
            availValue = 0;
        }
        String[] temp = {newId};
        ContentValues cV = new ContentValues();
        cV.put(col1, name);
        cV.put(col2, weightString);
        cV.put(col3, priceString);
        cV.put(col4, desc);
        cV.put(col5, availValue);
        //updates the row where prodname is the prodname selected from previous activity and updates all the edit text fields
        db.update(tableName,cV, "prodName=?",temp);
        products = new DBHandler(this);
        Cursor cursor = returnItems();
        showItems(cursor);
        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_SHORT).show();
    }
}
