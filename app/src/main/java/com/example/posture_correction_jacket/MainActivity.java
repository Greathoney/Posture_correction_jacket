package com.example.posture_correction_jacket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.posture_correction_jacket.R;

public class MainActivity extends AppCompatActivity {

    Button pairing_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pairing_button = findViewById(R.id.pairing_button);

        pairing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 레이아웃 및 액티비티를 전환하기 위한 코드
                Intent intent = new Intent(MainActivity.this, Bluetooth_pairingActivity.class);
                startActivity(intent);
            }
        });


    }
}
