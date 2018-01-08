package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * Created by Adrian on 12/8/2017.
 */

public class FichaTablero extends Actor {
    ArrayList<FichaTablero> vecinas;
    ArrayList<FichaTablero> centradas;
    ArrayList<FichaTablero> auxiliar = new ArrayList<FichaTablero>();
    protected int value;
    protected boolean vista;
    private TextureRegion graphic;

    public FichaTablero(float x, float y, int value, float lado, boolean vista) {
        this.value = value;
        this.vista = vista;

        graphic = Laps.circles.findRegion(String.valueOf(value));

        this.setBounds(x,y,lado,lado);
    }

    public Vector2 getPosition() {
        return new Vector2(getX(),getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(graphic, this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    public void changeValue(int newValue) {
        this.value = newValue;
        this.graphic = Laps.circles.findRegion(String.valueOf(value));
    }

    public boolean comprobar(FichaTablero f, ArrayList<FichaTablero> lista){
        if(!lista.isEmpty()) {
            for (FichaTablero ficha : lista) {
                if (ficha.getPosition() == f.getPosition()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void alCentro(FichaTablero ficha, FichaTablero origin) {
        ficha.changeValue(origin.value);
        origin.changeValue(0);
        ficha.vista = true;
        vecinasRev(ficha, ficha, ficha.value);
    }

    public boolean contarIgualesAdyacentes(FichaTablero ficha, FichaTablero inicial){
        for (FichaTablero v: ficha.vecinas) {
            if (v.value == ficha.value && comprobar(v, auxiliar) && !v.vista){
                v.vista = true;
                auxiliar.add(v);
                contarIgualesAdyacentes(v, inicial);
            }
        }
        if(ficha == inicial && auxiliar.size() >= 2) {
            return true;
        }
        return false;
    }

    public void vecinasRev(FichaTablero ficha, FichaTablero inicial, int valorInicial){
        if(contarIgualesAdyacentes(ficha, inicial)) {
            for (FichaTablero f : auxiliar) {
                f.unir(f);
            }
            ficha.vista = false;
            auxiliar = new ArrayList<FichaTablero>();
            ficha.changeValue(valorInicial + 1);
        }else {
            ficha.vista = false;
            adyacentesNoVistas(ficha);
            auxiliar = new ArrayList<FichaTablero>();
        }
        for(FichaTablero f: ficha.centradas){
            if (f.value == 0){
                ficha.alCentro(f, ficha);
            }
        }
    }

    public void adyacentesNoVistas(FichaTablero ficha){
        for (FichaTablero v: ficha.vecinas) {
            if (v.value == ficha.value && comprobar(v, auxiliar) && v.vista){
                v.vista = false;
                adyacentesNoVistas(v);
            }
        }
    }

    public void unir(FichaTablero aUnir){
        Vector2 posInicial = aUnir.getPosition();
                aUnir.setPosition(posInicial.x, posInicial.y);
                aUnir.changeValue(0);
                aUnir.vista = false;
    }
}