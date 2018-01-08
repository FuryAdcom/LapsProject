package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Tablero extends Stage{
    private Random numero;
    int score;
    Casilla casilla;
    FichaTablero changed;
    FichaRotatoria toThrow;
    FichaProxima next;

    public Tablero(Viewport viewport, final int dimension_casilla) {
        super(viewport);
        casilla = new Casilla(dimension_casilla);
        casilla.addToStage(this);
        numero = new Random();
        score = 0;

        //Rotatoria
        float lado_ficha = casilla.getLado_casilla();
        int valor = numero.nextInt(2)+1;
        toThrow = new FichaRotatoria(casilla.getX()-104, casilla.getY() - 104, valor, lado_ficha);
        this.addActor(toThrow);
        //Proxima
        valor = numero.nextInt(4)+1;
        next = new FichaProxima(casilla.getX() - 3.5f, casilla.getY() + 6 , valor, lado_ficha);
        this.addActor(next);

        this.addListener((new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toThrow = getToThrow();
                next = getNext();
                toThrow.setPosition(toThrow.lugarX, toThrow.lugarY);
                changed = casilla.calcularCasillaVacia(toThrow, 100);
                casilla.ordenarAlCentro(changed);
                toThrow.disparar(changed, toThrow, next);
                float delay = 0.4f;
                Timer.schedule(new Timer.Task(){
                    @Override
                    public void run() {
                        casilla.ordenarAlCentro(changed);
                        score += changed.score;
                    }
                }, delay);
                Gdx.input.setInputProcessor(new InputAdapter());
                return true;
            }
        }));
    }

    public FichaProxima getNext(){
        return next;
    }

    public FichaRotatoria getToThrow() {
        return toThrow;
    }
}