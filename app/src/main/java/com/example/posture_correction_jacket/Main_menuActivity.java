package com.example.posture_correction_jacket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main_menuActivity extends AppCompatActivity {

    Button menu1;
    Button help1;
    Button help2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        menu1 = findViewById(R.id.menu1);
        help1 = findViewById(R.id.help1);
        help2 = findViewById(R.id.help2);


        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Menu1_Activity.class);
                startActivity(intent);
            }
        });

        help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Help1_Activity.class);
                startActivity(intent);
            }
        });

        help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(Main_menuActivity.this, Help2_Activity.class);
                startActivity(intent);
            }
        });
    }
}
