package com.w1667474.mycupboard;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.w1667474.mycupboard.details.col1;
import static com.w1667474.mycupboard.details.tableName;

public class editProducts extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<String> items = new ArrayList<>();
    ArrayAdapter<String> ad;
    ListView list;
    DBHandler products;
    int DBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);
        list = findViewById(R.id.editProdList);
        list.setOnItemClickListener(this);
        products = new DBHandler(this);
        Cursor cursor = returnItems();
        showItems(cursor);
    }
    private Cursor returnItems(){
        SQLiteDatabase db = products.getReadableDatabase();
        Cursor cursor = db.query(tableName, new String[] {col1, _ID}, null, null, null, null, null);
        return cursor;
    }

    private void showItems(Cursor cursor) {
        list = findViewById(R.id.editProdList);
        //using an array adapter to format the listview
        ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);

        while (cursor.moveToNext()) {
            String prodName = cursor.getString(0);
            DBid = cursor.getInt(1);
            items.add(prodName);
        }
        cursor.close();
        products.close();
        list.setAdapter(ad);
    }

    @Override
    //passes the name of the item at the position to the producttoview activity
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), productToEdit.class);
        //intent.putExtra("id", DBid);
        intent.putExtra("id", items.get(position));
        startActivity(intent);
    }
}
