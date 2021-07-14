package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
<<<<<<< Updated upstream
    String url = "http://oreopie.ipdisk.co.kr:5000/transformer/post";

    final StringRequest stringRequest =
        new StringRequest(
            Request.Method.POST,
            url,
            new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                tv.setText(response);
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
=======
    String url = "http://192.168.0.2:3000/cgi-bin/index.py";

    final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        tv.setText(response);
      }
    }, error -> {
    }) {
      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("sentence", sentenceBox.getText().toString());
        return params;
      }
    };

    stringRequest.setTag(TAG);

    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        queue.add(stringRequest);
      }
    });
>>>>>>> Stashed changes
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (queue != null) {
      queue.cancelAll(TAG);
    }
  }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
