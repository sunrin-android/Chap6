package com.example.sunrin.chap6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 이벤트 소스
        button1 = findViewById(R.id.button1);
        // 이벤트 소스 -> 리스너 연결
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                button1.setText("내부 클래스로 이벤트 처리");
                Toast.makeText(getApplicationContext(), "이벤트 처리 성공", Toast.LENGTH_LONG).show();
            }
        }
    }
    // 이벤트 핸들러
//    @Override
//    public void onClick(View v) {
//        Toast.makeText(this, "이벤트 처리 성공", Toast.LENGTH_LONG).show();
//    }
}
