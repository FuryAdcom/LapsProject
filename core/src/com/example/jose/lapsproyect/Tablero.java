package com.example.jose.lapsproyect;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Tablero extends Stage {
    private Random numero;
    Casilla casilla;
    FichaRotatoria toThrow;

    public Tablero(Viewport viewport, int dimension_casilla) {
        super(viewport);
        casilla = new Casilla(dimension_casilla);
        casilla.addToStage(this);
        numero = new Random();

        float lado_ficha = casilla.getLado_casilla();
        int valor = numero.nextInt(2)+1;
        toThrow = new FichaRotatoria(casilla.getX() - 100, casilla.getY() - 100, valor, lado_ficha);
        this.addActor(toThrow);
    }

    public FichaRotatoria getToThrow() {
        return toThrow;
    }
}