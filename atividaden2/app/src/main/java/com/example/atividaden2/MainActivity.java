package com.example.atividaden2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.meuBotao);

        ListView listView = findViewById(R.id.mylistview);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://restcountries.eu/rest/v2/lang/it";
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                List<String> names = new ArrayList<String>();
                                //System.out.println(response);
                                try {
                                    JSONArray jsonObject = new JSONArray(response);
                                    for (int i = 0; i < jsonObject.length() ; i++) {
                                        names.add(jsonObject.getJSONObject(i).get("name").toString()+',');
                                    }

                                    listView.setAdapter(new ArrayAdapter<String>(
                                            getApplicationContext(),
                                            android.R.layout.simple_list_item_1,
                                            android.R.id.text1,
                                            names.toString().split(",")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                System.err.println(error.toString());
                            }
                        }
                );
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
            }
        });
    }
}