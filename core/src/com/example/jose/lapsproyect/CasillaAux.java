package com.example.jose.lapsproyect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;

/**
 * Created by Adrian on 1/6/2018.
 */

public class CasillaAux extends Actor{
    protected ArrayList<FichaTablero> huecos;
    private float lado;
    private float lado_ficha;
    TextureRegion casilla_border;
    private double angulo = 90;
    private double anguloSecundario = 60;

    public CasillaAux(int dimension) {
        casilla_border = Laps.circles.findRegion("table_cell");
        lado = CONSTANTES.ANCHO - 2*CONSTANTES.PADDING;
        this.setBounds((CONSTANTES.ANCHO/2)-0.5f,(CONSTANTES.LARGO - lado)/2, lado, lado);
        this.lado_ficha = lado/dimension;
        crearFichas(CONSTANTES.DIMENSION);
    }

    private void crearFichas(int dimension) {
        //Crea y llena la lista de Fichas que pertenecen al tablero.
        huecos = new ArrayList<FichaTablero>();
        float xMove = this.getX();
        float yMove = this.getY();
        int cantidad = 0;
        float radius = 0;
        FichaTablero ficha;
        for (int i = 0; i < dimension; i++) {
            if(i == 0){
                //Central
                ficha = new FichaTablero(xMove, yMove, 0, lado_ficha, false);
                huecos.add(ficha);
            }else{
                cantidad += 6*(dimension-i);
            }
        }
        for (int i = 0; i < cantidad; i++) {
            if ((angulo == 90) || angulo == 450) {
                angulo = 90;
                anguloSecundario = 60;
                radius++;
                anguloSecundario = anguloSecundario/radius;
                double x = xMove + (radius * Math.cos(Math.toRadians(angulo)));
                double y = yMove + (radius * Math.sin(Math.toRadians(angulo)));
                if(i <= 5) {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha, false);
                    huecos.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha, false);
                    huecos.add(ficha);
                }
                angulo += anguloSecundario;
            }else{
                double x = xMove + (radius * Math.cos(Math.toRadians(angulo)));
                double y = yMove + (radius * Math.sin(Math.toRadians(angulo)));
                if(i <= 5) {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha, false);
                    huecos.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha, false);
                    huecos.add(ficha);
                }
                angulo += anguloSecundario;
            }
        }
    }

    public void addToStage(Stage stage) {
        stage.addActor(this);
        for (FichaTablero c: huecos) {
            stage.addActor(c);
        }
    }
}
