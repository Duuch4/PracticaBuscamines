package com.example.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegistreFinal extends AppCompatActivity {

    TextView textView;
    TextView valorsLog;
    private EditText destinatari;
    private EditText Subjecte;
    private EditText Cos;
    Button enviar;
    private Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre_final);
        textView = findViewById(R.id.text_view_dia);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        textView.setText(dateTime);

        valorsLog = findViewById(R.id.valorsLog);
        Datos();
        destinatari = findViewById(R.id.email);
        Subjecte = findViewById(R.id.subject);
        Cos = findViewById(R.id.cos);
        enviar = findViewById(R.id.enviar);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destinataris = destinatari.getText().toString().trim(); // amb el trim treurem els espais d'abans i después
                String subject = Subjecte.getText().toString().trim();
                String Coss = Cos.getText().toString().trim();

                EnviarEmail (destinataris,subject,Coss);

            }
        });
    }

    private void Datos(){
        bundle = getIntent().getExtras();

        if(bundle.containsKey("Resultado")){
            System.out.println("conte resultado i es "+ bundle.getInt("Resultado"));
        }
        int res = bundle.getInt("Resultado");
        if(res==1){
            valorsLog.setText("Has ganado !!");
            if(bundle.containsKey("timeLeft")){
                int timeLeft = bundle.getInt("timeLeft");
                valorsLog.setText(valorsLog.getText()+"\n"+"Te han sobrado " + timeLeft + " segundos !");
            }
        }
        else {
            int celdasRestantes = bundle.getInt("CeldasRestantes");
            if (res == 2) {
                String pos = bundle.getString("Posicio");
                valorsLog.setText("Has perdido !!  Bomba en casilla " + pos + "\n Te han quedado " + celdasRestantes + " Casillas por descubrir");
            }
            if (res == 3) {
                valorsLog.setText("Has agotado el tiempo \n Te han quedado " + celdasRestantes + " Casillas por descubrir");
            }
        }
    }

    private void EnviarEmail(String destinataris, String subject, String Coss) {
        Intent emailIntent = new Intent (Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("Correu a:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[] {destinataris});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,Coss);

        try {
            startActivity(Intent.createChooser(emailIntent,"Tria un client de correu"));
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }


}