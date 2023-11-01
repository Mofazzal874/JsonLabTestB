package com.example.jsonlabtestb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView lst ;
    EditText txt ;

    ArrayList<String> clubs = new ArrayList<String>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = findViewById(R.id.clubNames) ;
        txt = findViewById(R.id.enterName) ;

        parseData() ;
    }

    private void parseData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this) ;
        String url = "https://api.myjson.online/v1/records/1ccb1aa4-56a7-4c58-8c49-69c098252243" ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String cName = jsonObject.getString("clubName");
                        clubs.add(cName);

                        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, clubs);
                        lst.setAdapter(arrayAdapter) ;

                    lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String input = txt.getText().toString();

                            if (input.equals("players")) {
                                Intent i = new Intent(MainActivity.this, PlayersActivity.class);
                                i.putExtra("clubName", clubs.get(position));
                                startActivity(i) ;
                            } else if (input.equals("stadium")) {
                                Intent i = new Intent(MainActivity.this, StadiumActivity.class);
                                i.putExtra("clubName", clubs.get(position));
                                startActivity(i);

                            }
                        }
                    });
                }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;
        requestQueue.add(jsonObjectRequest) ;
    }
}