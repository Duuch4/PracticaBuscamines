package com.example.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

public class Configuracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Button button = findViewById(R.id.buttonempezar);
        CheckBox checkBox = findViewById(R.id.checkBox);
        RadioGroup radioGroupGraella = findViewById(R.id.radiogroupgraella);
        RadioGroup radioGroupMines = findViewById(R.id.radiogroupmines);
        EditText alias = findViewById(R.id.editText);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, Joc.class);
            int tamanyGraella;
            int numeroMines;
            switch (radioGroupGraella.getCheckedRadioButtonId()){
                case R.id.radiob5:
                    tamanyGraella = 5;
                    break;
                case R.id.radiob6:
                    tamanyGraella = 6;
                    break;
                case R.id.radiob7:
                    tamanyGraella = 7;
                    break;
                default:
                    tamanyGraella = 7;
            }
            switch (radioGroupMines.getCheckedRadioButtonId()){
                case R.id.radiob15:
                    numeroMines = 15;
                    break;
                case R.id.radiob25:
                    numeroMines = 25;
                    break;
                case R.id.radiob35:
                    numeroMines = 35;
                    break;
                default:
                    numeroMines = 25;
            }
            intent.putExtra("Nombre",alias.getText().toString());
            intent.putExtra("Tamaño Tablero",tamanyGraella);
            intent.putExtra("Control Tiempo",checkBox.isChecked());
            intent.putExtra("Numero Minas",numeroMines);
            startActivity(intent);
            finish();
        });

    }
}