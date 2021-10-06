package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MAIN";
  static Gson gson = new Gson();
  static JsonData jsonData = gson.fromJson("{}", JsonData.class);
  private TextView tv;
  private EditText sentenceBox;
  private RequestQueue queue;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    tv = findViewById(R.id.transformerView);
    sentenceBox = findViewById(R.id.sentenceInput);
    Button btnSend = findViewById(R.id.button);

    queue = Volley.newRequestQueue(this);
    String url = "http://192.168.0.24:5000/transformer/post";

    final StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            response -> {
              jsonData = new Gson().fromJson(response, JsonData.class);
              Log.i(TAG + "RESPONSE", gson.toJson(jsonData));
              tv.setText(jsonData.getTransformer());
            },
            error -> {
            }) {
          @Override
          public byte[] getBody() {
            jsonData.setSentence(sentenceBox.getText().toString());
            Log.i(TAG, gson.toJson(jsonData));
            return gson.toJson(jsonData).getBytes(StandardCharsets.UTF_8);
          }

          @Override
          public Map<String, String> getHeaders() {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json; charset=utf-8");
            return headers;
          }
        };
    stringRequest.setTag(TAG);

    btnSend.setOnClickListener(
        v -> {
          tv.setText("");
          queue.add(stringRequest);
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
