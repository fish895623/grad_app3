package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MAIN";
  private TextView tv;
  private EditText sentenceBox;
  private Button btnSend;
  private RequestQueue queue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tv = findViewById(R.id.tvMain);
    sentenceBox = findViewById(R.id.sentence);
    btnSend = findViewById(R.id.btnSend);

    queue = Volley.newRequestQueue(this);
    String url = "http://192.168.0.3:5000/";

    final StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                JsonElement jsonElement =
                    new JsonParser().parseString(response); // "{\"sentence\": \"asdf\"}"
                JsonData jsonData = new Gson().fromJson(response, JsonData.class);

                tv.setText(jsonData.getSentence());
              }
            },
            error -> {}) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("sentence", sentenceBox.getText().toString());
            return params;
          }
        };
    stringRequest.setTag(TAG);

    btnSend.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            queue.add(stringRequest);
          }
        });
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (queue != null) {
      queue.cancelAll(TAG);
    }
  }
}
