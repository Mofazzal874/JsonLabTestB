package com.example.jsonlabtestb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StadiumActivity extends AppCompatActivity {


    TextView txt ;
    String cbName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium);
        txt = findViewById(R.id.stadium) ;

        Intent i = getIntent() ;
        cbName = i.getStringExtra("clubName") ;

        extractStadium() ;

    }

    private void extractStadium() {
        RequestQueue requestQueue = Volley.newRequestQueue(this) ;
        String url = "https://api.myjson.online/v1/records/1ccb1aa4-56a7-4c58-8c49-69c098252243" ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("data") ;
                    for(int i = 0 ; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        if((jsonObject.getString("clubName")).equals(cbName)){
                            JSONObject stadium = jsonObject.getJSONObject("stadium") ;
                            String nam = stadium.getString("name") ;
                            String capacity = stadium.getString("capacity") ;

                            txt.setText("Name: " + nam + "capacity: " + capacity+"\n");
                        }
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