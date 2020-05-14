package com.w1667474.mycupboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void registerProduct(View view) {
        startActivity(new Intent(MainActivity.this, RegisterProduct.class));
    }

    public void viewProducts (View view){
        startActivity(new Intent(MainActivity.this, displayProducts.class));
    }
    public void viewAvailability (View view){
        startActivity(new Intent(MainActivity.this, availability.class));
    }
    public void editProducts (View view){
        startActivity(new Intent(MainActivity.this, editProducts.class));
    }

    public void showSearch (View view){
        startActivity(new Intent(MainActivity.this, search.class));
    }

    public void showRecipe (View view){
        startActivity(new Intent(MainActivity.this, recipes.class));
    }
}
