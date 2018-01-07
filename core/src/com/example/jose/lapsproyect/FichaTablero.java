package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.Unmarshaller;

/**
 * Created by Adrian on 12/8/2017.
 */

public class FichaTablero extends Actor {
    ArrayList<FichaTablero> vecinas;
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

    public void contarIgualesAdyacentes(FichaTablero ficha, FichaTablero inicial){
        for (FichaTablero v: ficha.vecinas) {
            if (v.value == ficha.value && comprobar(v, auxiliar) && !v.vista){
                v.vista = true;
                auxiliar.add(v);
                contarIgualesAdyacentes(v, inicial);
            }
        }
        if(ficha == inicial && auxiliar.size() >= 2) {
            for (FichaTablero f : auxiliar) {
                f.unir(ficha, f);
            }
            auxiliar = new ArrayList<FichaTablero>();
            ficha.changeValue(inicial.value + 1);

        }
    }

    public void unir(final FichaTablero ficha, final FichaTablero aUnir){
        Vector2 targetPos = ficha.getPosition();
        final Vector2 posInicial = aUnir.getPosition();
        ThrowAction throwAction = new ThrowAction(targetPos);
        throwAction.setSpeed(7);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                aUnir.setPosition(posInicial.x, posInicial.y);
                aUnir.changeValue(0);
                Gdx.input.setInputProcessor(ficha.getStage());
                return true;
            }
        });
        this.addAction(sequenceAction);
    }

    public void alCentro(final FichaTablero ficha, final  FichaTablero origin){
        Vector2 targetPos = ficha.getPosition();
        final Vector2 posInicial = origin.getPosition();
        ThrowAction throwAction = new ThrowAction(targetPos);
        throwAction.setSpeed(7);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(ficha.value == 0) {
                    ficha.vista = true;
                    ficha.changeValue(origin.value);
                    contarIgualesAdyacentes(ficha, ficha);
                }
                return true;
            }
        });
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                origin.setPosition(posInicial.x, posInicial.y);
                origin.changeValue(0);
                Gdx.input.setInputProcessor(origin.getStage());
                return true;
            }
        });
        this.addAction(sequenceAction);
    }
}