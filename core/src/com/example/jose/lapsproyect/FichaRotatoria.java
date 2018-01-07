package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class FichaRotatoria extends Actor {
    protected int value;
    private TextureRegion graphic;
    private Random random;
    boolean solosi;

    protected float lugarX;
    protected float lugarY;
    private double angulo = -90;

    public FichaRotatoria(float x, float y, int value, float lado) {
        random = new Random();
        this.value = value;

        graphic = Laps.circles.findRegion(String.valueOf(value));

        this.setBounds(x,y,lado,lado);
    }

    public void changeValue(int newValue) {
        this.value = newValue;
        this.graphic = Laps.circles.findRegion(String.valueOf(value));
    }

    public Vector2 getPosition() {
        return new Vector2(getX(),getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(this.getPosition().x < -90) {
            if (Math.toRadians(angulo) <= Math.toRadians(-450)) {
                angulo = -90;
                double x = 4f - (3.5f * Math.cos(Math.toRadians(angulo)));
                double y = 4.4f - (3.5f * Math.sin(Math.toRadians(angulo)));
                lugarX = (float) x;
                lugarY = (float) y;
                batch.draw(graphic, lugarX, lugarY, this.getWidth(), this.getHeight());
            } else{
                double x = 4f - (3.5f * Math.cos(Math.toRadians(angulo)));
                double y = 4.4f - (3.5f * Math.sin(Math.toRadians(angulo)));
                lugarX = (float) x;
                lugarY = (float) y;
                angulo -= 2;
                batch.draw(graphic, lugarX, lugarY, this.getWidth(), this.getHeight());
            }
        }else {
            batch.draw(graphic, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    public void disparar(final FichaTablero targetCas, final FichaRotatoria throwingCas, final FichaProxima nextCas) {
        Vector2 targetPos = targetCas.getPosition();
        final ThrowAction throwAction = new ThrowAction(targetPos);
        throwAction.setSpeed(CONSTANTES.THROW_SPEED);

        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(targetCas.value == 0) {
                    targetCas.changeValue(throwingCas.value);
                    targetCas.vista = true;
                    targetCas.contarIgualesAdyacentes(targetCas, targetCas);
                }
                return true;
            }
        });
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                throwingCas.changeValue(nextCas.value);
                int index = random.nextInt(7)+1;
                nextCas.changeValue(index);
                throwingCas.setPosition(-104, -104);
                Gdx.input.setInputProcessor(targetCas.getStage());
                return true;
            }
        });
        this.addAction(sequenceAction);
    }
}
