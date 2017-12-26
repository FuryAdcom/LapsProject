package com.example.jose.lapsproyect;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class Tablero extends Stage{
    private Random numero;
    Casilla casilla;
    FichaRotatoria toThrow;
    FichaProxima next;
    FichaTablero ficha;

    public Tablero(Viewport viewport, int dimension_casilla) {
        super(viewport);
        casilla = new Casilla(dimension_casilla);
        casilla.addToStage(this);
        numero = new Random();

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
                toThrow.disparar(calcularCasillaVacia(toThrow), toThrow, next);
                //Perdemos el focus intencionalmente para no permitir cambio de direcciones
                Gdx.input.setInputProcessor(new InputAdapter());
                return true;
            }
        }));
    }

    public FichaTablero calcularCasillaVacia(FichaRotatoria lanzada){
        double xCentral = casilla.fichas.get(0).getPosition().x;
        double yCentral = casilla.fichas.get(0).getPosition().y;
        double distCentral = Math.sqrt(Math.pow(lanzada.getX() - xCentral, 2)+(Math.pow(lanzada.getY() - yCentral, 2)));
        System.out.println();
        System.out.print(xCentral + "/" + yCentral);

        for (FichaTablero f: casilla.fichas) {
            double x = f.getPosition().x;
            double y = f.getPosition().y;
            if(Math.sqrt(Math.pow(lanzada.getX() - x, 2)+(Math.pow(lanzada.getY() - y, 2))) < distCentral && f.value == 0){
                ficha = f;
            }
        }
        return ficha;
    }

    public FichaProxima getNext(){
        return next;
    }

    public FichaRotatoria getToThrow() {
        return toThrow;
    }
}