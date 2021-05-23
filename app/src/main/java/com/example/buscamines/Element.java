package com.example.buscamines;


public class Element {
    public Element(){}


    private boolean nodestapada; // No sabemos lo que hay en la casilla no destapada, "normal"

    public boolean Nodestapada() {
        return nodestapada;
    }
    public void Tapada(boolean Nodestapada) {
        nodestapada = Nodestapada;
    }


    private boolean bandera; // Si tiene la bandera

    public boolean TieneBandera() {
        return bandera;
    }

    public void PonerBandera(boolean TieneBandera) {
        bandera = TieneBandera;
    }


    private boolean mina; // Si tiene la mina

    public boolean TieneMina() {
        return mina;
    }
    public void PonerMina(boolean HayMina) {
        mina = HayMina;
    }

    private int minasalrededor; // Devuelve el numero de minas que tiene alrededor

    public int ObtenerMinas(){
        return minasalrededor;
    }

    public void MinasAlrededor(int MinasLado) {
        this.minasalrededor = MinasLado;
    }


    private String posicion;

    public void Posicion(int posicion){
        this.posicion = Integer.toString(posicion);
    }

    public String StringPosicion(){
        return this.posicion;
    }

    public void ObtenerMinas(int i) {
    }
}
