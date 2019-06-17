package com.example.myevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView text1;
    ScrollView scroll;
    GestureDetector detector; // 제스처 이벤트 처리를 위한 detector


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.text1);
        scroll = findViewById(R.id.scroll);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText("");
            }
        });
        detector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                println("onDown");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                println("onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                println("onShowPress");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                println("onScroll");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                println("onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                println("onFling");
                return false;
            }
        });

        detector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                println("onSingleTapConfimed");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                println("onDoubleTap");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }

    // 액티비티 이벤트 처리
    // 부모클래스 onTouchEvent() 콜백 이벤트
    float curX, curY;
    float oldDist, newDist;
    String mode = "none";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        int action = event.getActionMasked();
        curY = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                curX = event.getX();
                println("손가락 눌림: " + curX + ", " + curY);
                break;
            case MotionEvent.ACTION_MOVE:
//                println("손가락 움직임 : " + curX + ", " + curY);
                if (event.getPointerCount() >= 2) {
                    if (newDist - oldDist > 30) println("확대 zoom-in");
                    else if (oldDist - newDist > 30) println("축소 zoom-out");
                    oldDist = newDist;
                }
                newDist = spacing(event);
                break;
            case MotionEvent.ACTION_UP:
                if (mode == "none") {
                    float diffX = curX - event.getX();
                    if (diffX < 30) println("왼쪽으로 화면을 밀었습니다.");
                    else if (diffX < -30) println("오른쪽으로 화면을 밀었습니다.");
                    println("손가락 뗌 : " + curX + ", " + curY);
                }
                mode = "none";
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // 멀티터치
                mode = "zoom";
                println("pointer-down");
                break;
            case MotionEvent.ACTION_POINTER_UP: // 멀티터치
                println("pointer-up");
                break;
        }
        scroll.fullScroll(View.FOCUS_DOWN);
        return true;
    }

    public float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    public void println(String s) {
        text1.append(s + "\n");
    }

    long initTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "시스템 Back 버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
                initTime = System.currentTimeMillis();
            } else {
                finish();
            }

        }
//        return super.onKeyDown(keyCode, event);
        return true;
    }
}