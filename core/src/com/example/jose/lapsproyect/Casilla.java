package com.example.jose.lapsproyect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Casilla extends Actor {
    private ArrayList<FichaTablero> fichas;
    private Random random;
    private float lado;
    private float lado_ficha;
    TextureRegion casilla_border;
    private double angulo = Math.toRadians(90);
    private double base = Math.toRadians(90)+(Math.toRadians(60)*6);
    private double sesenta = Math.toRadians(60);

    public Casilla(int cantidadfichas) {
        casilla_border = Laps.circles.findRegion("table_cell");
        lado = CONSTANTES.ANCHO - 2*CONSTANTES.PADDING;
        this.setBounds(CONSTANTES.PADDING + 3.5f,(CONSTANTES.LARGO - lado)/2 + 2, lado, lado);
        this.lado_ficha = lado/cantidadfichas;
        random = new Random();
        this.setTouchable(Touchable.enabled);
        crearFichas(CONSTANTES.DIMENSION);
    }

    private void crearFichas(int dimension) {
        //Crea y llena la lista de Fichas que pertenecen al tablero.

        fichas = new ArrayList<FichaTablero>();
        float xMove = this.getX();
        float yMove = this.getY();

        int cantidad = 0;
        float radius = 0;
        int ficha_value;
        FichaTablero ficha;

        for (int i = 0; i < dimension; i++) {
            if(i == 0){
                //Central
                ficha_value = random.nextInt(4)+1;
                ficha = new FichaTablero(xMove, yMove, ficha_value, lado_ficha);
                fichas.add(ficha);
            }else{
                cantidad += 3*(Math.pow(2, dimension-i));
            }
        }

        for (int i = 1; i < 8; i++) {
            if ((angulo == Math.toRadians(90)) || (angulo >= base)) {
                System.out.print("Power=" + Math.toRadians(450));
                angulo = Math.toRadians(90);
                sesenta = Math.toRadians(60);
                radius++;
                sesenta = sesenta/radius;
                double x = xMove + (radius * Math.cos(angulo));
                double y = yMove + (radius * Math.sin(angulo));

                if(i <= 6) {
                    ficha_value = random.nextInt(4) + 1;
                    ficha = new FichaTablero((float) x, (float) y, ficha_value, lado_ficha);
                    fichas.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha);
                    fichas.add(ficha);
                }
                angulo += sesenta;
            }else{
                System.out.print("Power=" + angulo);
                double x = xMove + (radius * Math.cos(angulo));
                double y = yMove + (radius * Math.sin(angulo));

                if(i <= 6) {
                    ficha_value = random.nextInt(4) + 1;
                    ficha = new FichaTablero((float) x, (float) y, ficha_value, lado_ficha);
                    fichas.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha);
                    fichas.add(ficha);
                }
                angulo += sesenta;
            }
        }
    }

    public void addToStage(Stage stage) {
        stage.addActor(this);
        for (FichaTablero c: fichas) {
            stage.addActor(c);
        }
    }

    public float getLado_casilla() {
        return lado_ficha;
    }
}
