package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class FichaRotatoria extends Actor {
    public int value;
    private TextureRegion graphic;
    private Random random;

    public FichaTablero primera;

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
        if(this.getPosition().x < -60) {
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
        primera = targetCas;
        Vector2 targetPos = targetCas.getPosition();
        ThrowAction throwAction = new ThrowAction(targetPos);
        throwAction.setSpeed(CONSTANTES.THROW_SPEED);

        //Se posiciona y lanza la ficha, cambiando el valor del target
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                if(targetCas.value == 0) {
                    targetCas.changeValue(throwingCas.value);
                }
                return true;
            }
        });
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                //Delegar valor de ficha proxima
                throwingCas.changeValue(nextCas.value);
                int index = random.nextInt(7)+1;
                nextCas.changeValue(index);
                throwingCas.setPosition(-104, -104);
                //Devolvemos el focus al Stage para poder colocar otra Casilla
                Gdx.input.setInputProcessor(targetCas.getStage());
                return true;
            }
        });
        this.addAction(sequenceAction);
    }
}
