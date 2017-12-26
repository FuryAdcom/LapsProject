package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Tablero extends Stage {
    private Random numero;
    Casilla casilla;
    FichaRotatoria toThrow;
    FichaProxima next;

    public Tablero(Viewport viewport, int dimension_casilla) {
        super(viewport);
        casilla = new Casilla(dimension_casilla);
        casilla.addToStage(this);
        numero = new Random();

        //Rotatoria
        float lado_ficha = casilla.getLado_casilla();
        int valor = numero.nextInt(2)+1;
        toThrow = new FichaRotatoria(casilla.getX()-100, casilla.getY() - 100, valor, lado_ficha);
        this.addActor(toThrow);
        //Proxima
        valor = numero.nextInt(4)+1;
        next = new FichaProxima(casilla.getX() - 3.5f, casilla.getY() + 6 , valor, lado_ficha);
        this.addActor(next);

        /*if(Gdx.input.isTouched()){
            toThrow = getToThrow();
            next = getNext();
            toThrow.disparar(lanzada, prox);
        }*/
    }

    public FichaProxima getNext(){
        return next;
    }

    public FichaRotatoria getToThrow() {
        return toThrow;
    }
}