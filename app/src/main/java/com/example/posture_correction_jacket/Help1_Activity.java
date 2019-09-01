package com.example.posture_correction_jacket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Help1_Activity extends AppCompatActivity {

    Button return_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help1);
        return_ = findViewById(R.id.return_);


        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Help1_Activity.this, Main_menuActivity.class);
                startActivity(intent);
            }
        });

    }
}
