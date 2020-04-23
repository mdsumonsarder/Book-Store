package com.bookstore.mahir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import com.bookstore.mahir.R;

public class Spalash extends AppCompatActivity {
  private static int spalash_time = 3000;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.spalash_layout);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

        Intent intent = new Intent(Spalash.this, Login.class);
        startActivity(intent);
        finish();
      }
    }, spalash_time);
    {

    }
  }
}
