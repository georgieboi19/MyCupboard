package com.w1667474.mycupboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.tableName;

public class recipes extends AppCompatActivity {

    DBHandler products;
    List<String> items = new ArrayList<>();
    List<String> ingrediants = new ArrayList<>();
    LinearLayout lineardisplay;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        products = new DBHandler(this);
        Cursor cursor = returnItems();
        showItems(cursor);
    }
    //get items where product is availible from db
    private Cursor returnItems(){
        SQLiteDatabase db = products.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] {col1}, "prodAvail=?", new String[]{String.valueOf(1)}, null, null, null);
        return cursor;
    }
//show the availbe items
    private void showItems(Cursor cursor) {
        lineardisplay = findViewById(R.id.linear_display);

        while (cursor.moveToNext()) {
            String prodName = cursor.getString(0);
            items.add(prodName);
        }
        for (int i = 0; i < items.size(); i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            String temp = items.get(i);
            cb.setText(temp);
            cb.setId(i);
            cb.setChecked(false);
            lineardisplay.addView(cb);
        }
        cursor.close();
        products.close();
    }
    //goes to recipes results on click and passes the items checked
    public void updateAvailablity (View view){
        for (int i= 0; i<items.size(); i++){
            String temp = Integer.toString(i);
            check = findViewById(getResources().getIdentifier(temp,"id", getPackageName()));
            Log.i("id is", temp);
            if (check.isChecked()){
                ingrediants.add(check.getText().toString());
                Log.i("added to Kitchen:", temp);
            }
        }
        Intent intent = new Intent(getBaseContext(), recipeResults.class);
        intent.putStringArrayListExtra("array", (ArrayList<String>) ingrediants);
        startActivity(intent);
    }
}
