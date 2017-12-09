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

    public Casilla(int cantidadfichas) {
        casilla_border = Laps.circles.findRegion("table_cell");
        lado = CONSTANTES.ANCHO - 2*CONSTANTES.PADDING;
        this.setBounds(CONSTANTES.PADDING + 3.5f,(CONSTANTES.LARGO - lado)/2 + 2, lado, lado);
        this.lado_ficha = lado/cantidadfichas;
        random = new Random();
        this.setTouchable(Touchable.enabled);
        crearFichas();
    }

    private void crearFichas() {
        //Crea y llena la lista de Fichas que pertenecen al tablero.

        fichas = new ArrayList<FichaTablero>();
        float xMove = this.getX();
        float yMove = this.getY();

        //Central
        int ficha_value = random.nextInt(4)+1;
        FichaTablero ficha = new FichaTablero(xMove, yMove, ficha_value, lado_ficha, false);
        fichas.add(ficha);
        //First Tier
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove, yMove + 1 , ficha_value, lado_ficha, false);
        fichas.add(ficha);
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove + 0.9f, yMove + 0.5f , ficha_value, lado_ficha, false);
        fichas.add(ficha);
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove + 0.9f, yMove - 0.5f , ficha_value, lado_ficha, false);
        fichas.add(ficha);
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove, yMove - 1 , ficha_value, lado_ficha, false);
        fichas.add(ficha);
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove - 0.9f, yMove - 0.5f , ficha_value, lado_ficha, false);
        fichas.add(ficha);
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove - 0.9f, yMove + 0.5f , ficha_value, lado_ficha, false);
        fichas.add(ficha);
        //Second Tier
        ficha = new FichaTablero(xMove, yMove + 2 , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove + 0.9f, yMove + 1.5f , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove + 1.8f, yMove + 1 , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove + 1.8f, yMove, 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove + 1.8f, yMove - 1 , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove + 0.9f, yMove - 1.5f , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove, yMove - 2 , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove - 0.9f, yMove - 1.5f , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove - 1.8f, yMove - 1 , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove - 1.8f, yMove, 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove - 1.8f, yMove + 1 , 0, lado_ficha, false);
        fichas.add(ficha);
        ficha = new FichaTablero(xMove - 0.9f, yMove + 1.5f , 0, lado_ficha, false);
        fichas.add(ficha);

        //Proxima ficha
        ficha_value = random.nextInt(4)+1;
        ficha = new FichaTablero(xMove - 4.5f, yMove + 6 , ficha_value, lado_ficha, true);
        fichas.add(ficha);
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
