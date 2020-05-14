package com.w1667474.mycupboard;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class recipeResults extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<String> ingrediants;
    List<String> items = new ArrayList<>();
    List<String> links = new ArrayList<>();
    String queryString;
    TextView header;
    ListView recipeList;
    ArrayAdapter<String> ad;
    String head2 = "No recipes available containing these ingredients, try different ingredients";
    String temp1 = "Results:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_results);
        recipeList = findViewById(R.id.viewRecipeList);
        items.clear();
        ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        header = findViewById(R.id.textView_results);
        recipeList.setOnItemClickListener(this);
        ingrediants = getIntent().getStringArrayListExtra("array");
        prepareQuery();
        new getRecipe().execute(queryString);
        recipeList.setAdapter(ad);
        isReturn();
    }
//check a recipe is returned
    public void isReturn(){
        if (items.size() == 0){
            String temp = "No recipes available containing these ingredients, try different ingredients";
            header.setText(temp);
        } else {
            String temp = "Results:";
            header.setText(temp);
        }
    }
//creates string for the query to get the daqta
    public void prepareQuery (){
        StringBuilder sB = new StringBuilder();
        for (int i=0; i <ingrediants.size();i++){
            String temp = ingrediants.get(i);
            sB.append(temp).append(",");
        }
        sB.setLength(sB.length() - 1);
        for(int j = 0; j<sB.length();j++){
            if(Character.isWhitespace(sB.charAt(j))){
                String temp = "%20";
                char c = temp.charAt(0);
                sB.setCharAt(j, c);
            }
        }
        Log.i("Query is", sB.toString());
        queryString = sB.toString();
    }

    @Override
    //gets the position and the url fro the links array holding the url of each item
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(position)));
        Log.i("browser URL=", links.get(position));
        startActivity(openBrowser);
    }

    public class getRecipe extends AsyncTask<String, Void, String>{
        List<String> items = new ArrayList<>();

        @Override
        //getting parts of the parsed json and adding to arrays in order to print and access the urls
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jObject = new JSONObject(s);
                JSONArray recipeArray = jObject.getJSONArray("recipes");
                int i = 0;
                //String title = null;
                while (i<recipeArray.length()){
                    JSONObject recipe = recipeArray.getJSONObject(i);
                    try {
                        String title = recipe.getString("title");
                        String URL = recipe.getString("source_url");
                        items.add(title);
                        links.add(URL);
                        Log.i("recipe title", title);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    i++;
                }
                if (items.size() != 0) {
                    header.setText(temp1);
                } else {
                    header.setText(head2);
                }
            } catch (JSONException e){
                e.printStackTrace();

            }
            for (int j = 0; j <items.size(); j++){
                String temp = items.get(j);
                ad.add(temp);
                //header.setText("Results:");
            }
            ad.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            return NetworkUtils.getRecipeInfo(strings[0]);
        }
    }
}
