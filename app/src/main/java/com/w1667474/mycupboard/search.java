package com.w1667474.mycupboard;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.col4;
import static com.w1667474.mycupboard.details.tableName;

public class search extends AppCompatActivity {

    DBHandler products;
    EditText eText;
    String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        products = new DBHandler(this);
    }

    public void startSearch (View view){
        Log.i("works", "yes");
        eText = findViewById(R.id.textToSearch);
        searchText = eText.getText().toString();
        //showItems(cursor);
        Log.i("works", searchText);
//searches where col1 or col4 is like the search param entered to the edit text
        SQLiteDatabase db = products.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] {col1}, col1 +" LIKE ? OR  " + col4 + " LIKE ?", new String[]{"%"+searchText+"%", "%"+searchText+"%"}, null, null, null);
        LinearLayout lineardisplay = findViewById(R.id.linear_display);
        TextView tV = new TextView(getApplicationContext());
        tV.setText("");
        lineardisplay.removeAllViews();
        StringBuilder sB = new StringBuilder();
        while (cursor.moveToNext()) {
            String prodName = cursor.getString(0);
            Log.i("from db", prodName);
            sB.append(prodName).append("\n");
        }
        tV.setText(sB);
        lineardisplay.addView(tV);

        cursor.close();
        products.close();
    }
}
