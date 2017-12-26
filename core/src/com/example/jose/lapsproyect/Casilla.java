package com.example.jose.lapsproyect;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Casilla extends Actor {
    protected ArrayList<FichaTablero> fichas;
    private Random random;
    private float lado;
    private float lado_ficha;
    TextureRegion casilla_border;
    private double angulo = 90;
    private double anguloSecundario = 60;

    public Casilla(int dimension) {
        casilla_border = Laps.circles.findRegion("table_cell");
        lado = CONSTANTES.ANCHO - 2*CONSTANTES.PADDING;
        this.setBounds((CONSTANTES.ANCHO/2)-0.5f,(CONSTANTES.LARGO - lado)/2, lado, lado);
        this.lado_ficha = lado/dimension;
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
                    ficha_value = random.nextInt(4) + 1;
                    ficha = new FichaTablero((float) x, (float) y, ficha_value, lado_ficha);
                    fichas.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha);
                    fichas.add(ficha);
                }
                angulo += anguloSecundario;
            }else{
                double x = xMove + (radius * Math.cos(Math.toRadians(angulo)));
                double y = yMove + (radius * Math.sin(Math.toRadians(angulo)));
                if(i <= 5) {
                    ficha_value = random.nextInt(4) + 1;
                    ficha = new FichaTablero((float) x, (float) y, ficha_value, lado_ficha);
                    fichas.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha);
                    fichas.add(ficha);
                }
                angulo += anguloSecundario;
            }
        }
    }

    public FichaTablero calcularCasillaVacia(FichaRotatoria lanzada){
        FichaTablero ficha = fichas.get(0);
        double xCentral = fichas.get(0).getPosition().x;
        double yCentral = fichas.get(0).getPosition().y;
        double distCentral = Math.sqrt(Math.pow(lanzada.getX() - xCentral, 2)+(Math.pow(lanzada.getY() - yCentral, 2)));

        for (FichaTablero f: fichas) {
            double x = f.getPosition().x;
            double y = f.getPosition().y;
            if(Math.sqrt(Math.pow(lanzada.getX() - x, 2)+(Math.pow(lanzada.getY() - y, 2))) <= distCentral){
                ficha = f;
            }
        }
        return ficha;
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
