package com.w1667474.mycupboard;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.w1667474.mycupboard.details.tableName;
import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.col5;
import static com.w1667474.mycupboard.details.col2;
import static com.w1667474.mycupboard.details.col3;
import static com.w1667474.mycupboard.details.col4;

public class RegisterProduct extends AppCompatActivity {

    EditText productName, productWeight, productPrice, productDesc;
    private DBHandler products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        //creates new DBhandler class
        products = new DBHandler(this);
    }

    public void saveButton(View view){
        //gets and stores edit texts so they can be read to the db
        products = new DBHandler(this);
        productName = findViewById(R.id.productName);
        productWeight = findViewById(R.id.productWeight);
        productPrice = findViewById(R.id.productPrice);
        productDesc = findViewById(R.id.productDesc);
        String answer1 = productName.getText().toString();
        String answer2 = productWeight.getText().toString();
        String answer3 = productPrice.getText().toString();
        String answer4 = productDesc.getText().toString();
        try {
            //try to add the product passing the entered edittext details
            addProduct(answer1, answer2, answer3, answer4);
        } finally {
            products.close();
        }

    }
//method that takes the stored edit text
    public void addProduct (String productName, String productWeight, String productPrice, String productDesc){
        //gets the database and makes it editable
        SQLiteDatabase db = products.getWritableDatabase();
        //stores the values to add in a content value
        ContentValues value = new ContentValues();
        //adding content to the content value
        value.put(col1, productName);
        value.put(col2, productWeight);
        value.put(col3, productPrice);
        value.put(col4, productDesc);
        //add column 5 as default 0. This means that the prod is not availible
        value.put(col5, 0);
        //this line inserts a new row into the db using the content values
        db.insert(tableName, null, value);
        //closes the db connection
        db.close();

        //provides ui feedback to user
        Toast.makeText(getApplicationContext(),"Item Saved",Toast.LENGTH_SHORT).show();
    }
}
