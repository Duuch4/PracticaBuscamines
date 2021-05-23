package com.example.buscamines;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Joc extends AppCompatActivity {
    public GridView tableroT;
    private String NombreJugador;
    private int TamañoTablero;
    private boolean ControlTiempo;
    private int NumeroMinas;
    public int Minas;
    public List<Element> tabla;
    private Bundle bundle;
    public Adapter Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc);
        Datos();
        this.tableroT = (GridView)findViewById(R.id.Boom);
        TextView t2 = (TextView)findViewById(R.id.NumeroCaselles) ;
        this.Adapter = new Adapter(this, this.tabla, this.TamañoTablero, this.NumeroMinas, this.NombreJugador, t2);
        if(ControlTiempo==true){
            TextView t = (TextView)findViewById(R.id.Contador);

            Adapter.CountDown(t);
        }
        this.tableroT.setNumColumns(TamañoTablero);
        this.tableroT.setAdapter(this.Adapter);
    }

    private void Datos(){
        bundle = getIntent().getExtras();
        System.out.println("nombre: " + bundle.getString("Nombre"));
        NombreJugador = bundle.getString("Nombre");
        TamañoTablero = bundle.getInt("Tamaño Tablero");
        ControlTiempo = bundle.getBoolean("Control Tiempo");
        NumeroMinas = bundle.getInt("Numero Minas");
        Minas = calculateMinesToGrill(TamañoTablero, NumeroMinas);
        CreacionTabla();
    }

    public void CreacionTabla(){
        tabla = new ArrayList<Element>();
        int totalSizeGrill = TamañoTablero * TamañoTablero;
        int mines = 0;
        for(int i = 0; i < totalSizeGrill; i++) {
            Element e = new Element();

            e.Tapada(true);
            e.PonerBandera(false);
            e.PonerMina(false);
            e.ObtenerMinas(0);
            e.Posicion(i);

            tabla.add(e);
        }
        while (mines < Minas){
            Random randomGenerator = new Random();
            int numRandom = randomGenerator.nextInt(totalSizeGrill);
            Element e = tabla.get(numRandom);
            if (!e.TieneMina()){
                e.PonerMina(true);
                tabla.set(numRandom,e);
                mines = mines + 1;
            }
        }
        checkMinesAround();
    }




    public int calculateMinesToGrill(int size, int percent){
        return (int)((size*size)*(percent/100.0));
    }



    public void checkMinesAround() {
        int totalSizeGrill = TamañoTablero * TamañoTablero;
        List<Integer> num = new ArrayList<>();
        for (int i = 2; i < TamañoTablero; i++){
            num.add((TamañoTablero *i)-1);
        }
        for (int pos = 0; pos< totalSizeGrill; pos++){
            if (pos == 0) {
                Element e = tabla.get(pos);
                if (e.TieneMina()) {
                    putNumMines(pos + 1);
                    putNumMines(TamañoTablero);
                    putNumMines(TamañoTablero + 1);
                }
            }
            else if (pos == TamañoTablero - 1){
                Element e = tabla.get(pos);
                if (e.TieneMina()){
                    putNumMines(pos - 1);
                    putNumMines((pos*2)+1);
                    putNumMines(pos*2);
                }
            }
            else if (pos == totalSizeGrill - TamañoTablero){
                Element e = tabla.get(pos);
                if (e.TieneMina()){
                    putNumMines(pos + 1);
                    putNumMines(pos - TamañoTablero);
                    putNumMines(pos - TamañoTablero + 1);
                }
            }
            else if (pos == totalSizeGrill - 1){
                Element e = tabla.get(pos);
                if (e.TieneMina()){
                    putNumMines(pos - 1);
                    putNumMines(((TamañoTablero - 1) * TamañoTablero) - 1);
                    putNumMines(((TamañoTablero - 1) * TamañoTablero) - 2);
                }
            }
            else if(pos > 0 && pos < TamañoTablero - 1){
                Element e = tabla.get(pos);
                if (e.TieneMina()){
                    putNumMines(pos - 1);
                    putNumMines(pos + 1);
                    putNumMines(pos + TamañoTablero);
                    putNumMines(pos + TamañoTablero - 1);
                    putNumMines(pos + TamañoTablero + 1);
                }
            }
            else if (pos > totalSizeGrill - TamañoTablero && pos < totalSizeGrill - 1){
                Element e = tabla.get(pos);
                if (e.TieneMina()){
                    putNumMines(pos - 1);
                    putNumMines(pos + 1);
                    putNumMines(pos - TamañoTablero);
                    putNumMines(pos - TamañoTablero + 1);
                    putNumMines(pos - TamañoTablero - 1);
                }
            }
            else if (pos% TamañoTablero == 0 && pos != 0 && pos != totalSizeGrill- TamañoTablero){
                Element e = tabla.get(pos);
                if (e.TieneMina()){
                    putNumMines(pos - TamañoTablero);
                    putNumMines(pos - TamañoTablero + 1);
                    putNumMines(pos + 1);
                    putNumMines(pos + TamañoTablero);
                    putNumMines(pos + TamañoTablero + 1);
                }
            }
            else if(num.contains(pos)){
                Element e = tabla.get(pos);
                if ((e.TieneMina())){
                    putNumMines(pos - 1);
                    putNumMines(pos - TamañoTablero - 1);
                    putNumMines(pos - TamañoTablero);
                    putNumMines(pos + TamañoTablero - 1);
                    putNumMines(pos + TamañoTablero);
                }
            }
            else{
                Element e = tabla.get(pos);
                if ((e.TieneMina())){
                    putNumMines(pos - 1);
                    putNumMines(pos - TamañoTablero - 1);
                    putNumMines(pos - TamañoTablero);
                    putNumMines(pos - TamañoTablero + 1);
                    putNumMines(pos + 1);
                    putNumMines(pos + TamañoTablero - 1);
                    putNumMines(pos + TamañoTablero);
                    putNumMines(pos + TamañoTablero + 1);
                }
            }
        }
    }
    private void putNumMines(int position){
        Element element = tabla.get(position);
        if (!element.TieneMina()){
            element.MinasAlrededor(element.ObtenerMinas()+1);
            tabla.set(position,element);
        }
    }
}
