package com.example.buscamines;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> stringArrayList;
    int size,bombs;
    String values;
    int timeLeft = 60;
    int celdasRestantes;
    List<Element> tablero;
    TextView textCeldasRestantes;
    int seconds=0;
    long startTime = System.currentTimeMillis();

    public Adapter(Context context, List<Element> tablero, int size, int bombs, String values, TextView textCeldasRestantes) {
        this.textCeldasRestantes = textCeldasRestantes;
        this.context = context;
        this.tablero = tablero;
        this.size = size;
        //this.controlTiempo = controlTiempo;
        this.bombs = bombs;
        this.values = values;
        celdasRestantes = size*size;
        textCeldasRestantes.setText(""+celdasRestantes + " Caselles per descobrir");
        //timeHandler.postDelayed(timerRunnable, 0);
    }

    /*Handler timeHandler = new Handler();
    Runnable timerRunnable = () -> {
        long millis = System.currentTimeMillis() - startTime;
        seconds = (int) millis / 1000;
        timeHandler.postDelayed((Runnable) this.timeHandler, 1000);
        System.out.println(seconds);
    };*/

    @Override
    public int getCount() {
        return size*size;
    }

    @Override
    public Element getItem(int position) {
        return tablero.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button btn;
        if (convertView == null){
            btn = new Button(context);
            btn.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT,GridView.LayoutParams.WRAP_CONTENT));
        }
        else{
            btn = (Button)convertView;
        }
        btn.setPadding(8,8,8,8);
        btn.setId(position);
        btn.setOnClickListener(new MyOnClickListener(position));
        btn.setOnLongClickListener(new MyOnLongClickListener(position));
        return btn;
    }


    public void CountDown(TextView textView){
        System.out.println("hola");
        String FORMAT = "%02d:%02d:%02d";

        /*cTimer = */new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText(""+ millisUntilFinished / 1000);
                timeLeft = (int) millisUntilFinished/1000;
            }

            @Override
            public void onFinish() {
                PartidaPerdida();
            }

        }.start();
    }

    private void RevisarJoc(){
        boolean NoHayBombas = true;
        for(int i = 0; i < tablero.size(); i++){
            Element celda = tablero.get(i);
            if (celda.Nodestapada() && !celda.TieneMina()){
                NoHayBombas = false;
            }
        }
        if(NoHayBombas){
            PartidaGanada();
        }
    }

    private void PartidaGanada() {
        Activity a = (Activity)context;
        Intent in = new Intent(context, RegistreFinal.class);
        AddExtras(in);
        in.putExtra("Resultado",1);
        if(timeLeft!=60){
            in.putExtra("timeLeft", timeLeft);
        }
        a.startActivity(in);
        a.finish();
    }

    private void PartidaPerdida(String pos) {
        Activity a = (Activity)context;
        Intent in = new Intent(context, RegistreFinal.class);
        AddExtras(in);
        in.putExtra("Resultado",2);  //derrota per mina
        in.putExtra("Posicio", pos);
        in.putExtra("CeldasRestantes", celdasRestantes);
        a.startActivity(in);
        a.finish();
    }

    private void PartidaPerdida() {
        Activity a = (Activity)context;
        Intent in = new Intent(context, RegistreFinal.class);
        AddExtras(in);
        in.putExtra("Resultado",3);  //derrota per timeOut
        in.putExtra("CeldasRestantes", celdasRestantes);
        a.startActivity(in);
        a.finish();
    }

    private void AddExtras(Intent in){
        in.putExtra("Nombre", values);
        in.putExtra("Casillas", size*size);
        in.putExtra("PorcentajeMinas", bombs);
        in.putExtra("NumeroMinas", (int)((size*size)*(bombs/100.0)));


    }



    public class MyOnLongClickListener extends Activity implements View.OnLongClickListener{

        private final int position;
        public MyOnLongClickListener(int position){this.position = position;}

        @Override
        public boolean onLongClick(View v) {
            notifyDataSetChanged();
            Button btn = (Button) v.findViewById(v.getId());
            Element celda = tablero.get(position);
            if (celda.Nodestapada()){
                celda.Tapada(false);
                celda.PonerBandera(true);
                btn.setText("🚩");
                tablero.set(position, celda);
            }
            else if (!celda.Nodestapada() && celda.TieneBandera()){
                celda.Tapada(true);
                celda.PonerBandera(false);
                btn.setText(null);
                tablero.set(position, celda);
            }
            RevisarJoc();
            return true;
        }
    }

    public class MyOnClickListener extends Activity implements View.OnClickListener {
        public final int position;
        public String gameOverPosition;
        public MyOnClickListener(int position){
            this.position=position;
        }
        @Override
        public void onClick(View view){
            celdasRestantes -=1;
            textCeldasRestantes.setText(""+celdasRestantes + " Caselles per descobrir");
            notifyDataSetChanged();
            Button btn = (Button) view.findViewById(view.getId());
            Element celda = tablero.get(position);
            System.out.println("position: "+ position + " celda: "+ celda);
            if(celda.Nodestapada()){
                celda.Tapada(false);
                tablero.set(position, celda);
                if(celda.TieneMina()){
                    gameOverPosition = celda.StringPosicion();
                    btn.setText("💣");
                    int fila = ((position+1)/size)+1;

                    double temp = (position+1) % size;
                    long iPart = (long) temp;
                    temp = temp-iPart;
                    int columna = (int) temp * size;
                    PartidaPerdida(""+fila+","+columna);
                }
                else{
                    btn.setText((CharSequence)Integer.toString(celda.ObtenerMinas()));
                }
            }
            RevisarJoc();
        }
    }
}
