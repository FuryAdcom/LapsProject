package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Casilla extends Actor {
    ArrayList<FichaTablero> fichas;
    private Random random;
    private float lado;
    private float lado_ficha;
    TextureRegion casilla_border;
    private double angulo = -90;
    private double anguloSecundario = -60;

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
                ficha = new FichaTablero(xMove, yMove, ficha_value, lado_ficha, false);
                fichas.add(ficha);
            }else{
                cantidad += 6*(dimension-i);
            }
        }
        for (int i = 0; i < cantidad; i++) {
            if (-angulo == 90 || -angulo == 450) {
                angulo = -90;
                anguloSecundario = 60;
                radius++;
                anguloSecundario = anguloSecundario/radius;
                double x = xMove - (radius * Math.cos(Math.toRadians(angulo)));
                double y = yMove - (radius * Math.sin(Math.toRadians(angulo)));
                if(i <= 5) {
                    ficha_value = random.nextInt(4) + 1;
                    ficha = new FichaTablero((float) x, (float) y, ficha_value, lado_ficha, false);
                    fichas.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha, false);
                    fichas.add(ficha);
                }
                angulo -= anguloSecundario;
            }else{
                double x = xMove - (radius * Math.cos(Math.toRadians(angulo)));
                double y = yMove - (radius * Math.sin(Math.toRadians(angulo)));
                if(i <= 5) {
                    ficha_value = random.nextInt(4) + 1;
                    ficha = new FichaTablero((float) x, (float) y, ficha_value, lado_ficha, false);
                    fichas.add(ficha);
                }else {
                    ficha = new FichaTablero((float) x, (float) y, 0, lado_ficha, false);
                    fichas.add(ficha);
                }
                angulo -= anguloSecundario;
            }
        }
        vecindad(dimension);
    }

    public void vecindad(int dimension){
        double distancia = 1.3;
        for(FichaTablero f: fichas){
            double x = f.getPosition().x;
            double y = f.getPosition().y;
            ArrayList<FichaTablero> vecinas = new ArrayList<FichaTablero>();
            f.vecinas = vecinas;
            for (FichaTablero cadaUna: fichas) {
                double x2 = cadaUna.getPosition().x;
                double y2 = cadaUna.getPosition().y;
                double distFicha = Math.sqrt(Math.pow(x2 - x, 2)+(Math.pow(y2 - y, 2)));
                if(distFicha < distancia && distFicha != 0){
                    vecinas.add(cadaUna);
                }
            }
        }
        haciaCentro(dimension);
    }

    private boolean comprobar(FichaTablero f, ArrayList<FichaTablero> lista){
        if(!lista.isEmpty()) {
            for (FichaTablero ficha : lista) {
                if (fichas.indexOf(f) == fichas.indexOf(ficha)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void haciaCentro(int dimension) {
        int limite = dimension-1;
        for (FichaTablero f : fichas) {
            double distancia = 100;
            FichaTablero central = fichas.get(0);
            double xCentral = central.getPosition().x;
            double yCentral = central.getPosition().y;
            double distCentral = Math.sqrt(Math.pow(f.getPosition().x - xCentral, 2) + (Math.pow(f.getPosition().y - yCentral, 2)));
            ArrayList<FichaTablero> centradas = new ArrayList<FichaTablero>();
            f.centradas = centradas;
            for (FichaTablero one : fichas) {
                if (fichas.indexOf(one) < fichas.indexOf(f) && limite > 0) {
                    double x = one.getPosition().x;
                    double y = one.getPosition().y;
                    double distFicha = Math.sqrt(Math.pow(f.getPosition().x - x, 2) + (Math.pow(f.getPosition().y - y, 2)));
                    if (distFicha > 0 && fichas.indexOf(f) < 7) {
                        if(comprobar(central, centradas)) {
                            centradas.add(central);
                        }
                    } else {
                        if (centradas.isEmpty()) {
                            if(comprobar(central, centradas)) {
                                centradas.add(central);
                            }
                            limite--;
                        } else if (distFicha > 0 && distFicha <= distCentral && distFicha < distancia) {
                            central = one;
                            distancia = distFicha;
                        } else if (distFicha > 0 && distFicha <= distCentral && distFicha >= distancia) {
                            if(distFicha < distancia+0.0000001) {
                                if(comprobar(central, centradas)) {
                                    centradas.add(central);
                                }
                                if(comprobar(fichas.get(fichas.indexOf(one)), centradas)) {
                                    centradas.add(fichas.get(fichas.indexOf(one)));
                                }
                                limite--;
                            }else if(distFicha+0.0000001 > Math.sqrt(Math.pow(f.getPosition().x - fichas.get(fichas.indexOf(one)+1).getPosition().x, 2) + (Math.pow(f.getPosition().y - fichas.get(fichas.indexOf(one)+1).getPosition().y, 2)))){
                                if(comprobar(fichas.get(fichas.indexOf(central)-1), centradas)) {
                                    centradas.add(fichas.get(fichas.indexOf(central)-1));
                                }
                                if(comprobar(central, centradas)) {
                                    centradas.add(central);
                                }
                                limite--;
                            }else{
                                if(comprobar(central, centradas)) {
                                    centradas.add(central);
                                }
                                limite--;
                            }
                        }
                    }
                }
            }
            limite = dimension-1;
        }
    }

    public void ordenarAlCentro(FichaTablero lanzada, FichaRotatoria rotatoria) {
        boolean cambiada = false;
        for (FichaTablero c : lanzada.centradas) {
            if (c.value == 0 && !cambiada) {
                lanzada.alCentro(c, lanzada, rotatoria);
                cambiada = true;
            }
        }
        cambiada = false;
        for (FichaTablero f : fichas) {
            if(f.value != 0) {
                for (FichaTablero c : f.centradas) {
                    if (c.value == 0 && !cambiada) {
                        f.alCentro(c, f, rotatoria);
                        cambiada = true;
                    }
                }
                cambiada = false;
            }
        }
    }

    public FichaTablero calcularCasillaVacia(FichaRotatoria lanzada, double distance){
        FichaTablero ficha = fichas.get(0);
        double xCentral = fichas.get(0).getPosition().x;
        double yCentral = fichas.get(0).getPosition().y;
        double distCentral = Math.sqrt(Math.pow(lanzada.getX() - xCentral, 2)+(Math.pow(lanzada.getY() - yCentral, 2)));

        for (FichaTablero f: fichas) {
            double x = f.getPosition().x;
            double y = f.getPosition().y;
            double distFicha = Math.sqrt(Math.pow(lanzada.getX() - x, 2)+(Math.pow(lanzada.getY() - y, 2)));
            if(distFicha < distCentral && distFicha < distance){
                ficha = f;
                distance = distFicha;
            }else if(distFicha < distCentral && distFicha > distance && ficha.value == 0){
                return ficha;
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
