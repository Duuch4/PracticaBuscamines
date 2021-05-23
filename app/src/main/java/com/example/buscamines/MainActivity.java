package com.example.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, Ayuda.class);

            startActivity(intent);
            finish();
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(this, Configuracion.class);

            startActivity(intent);
            finish();
        });
    }

    // EL BOTÓN DE SALIR
    public void exit(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
        System.exit(1);
    }
}