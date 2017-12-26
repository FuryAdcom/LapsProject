package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.Random;

/**
 * Created by Adrian on 11/12/2017.
 */

public class FichaRotatoria extends Actor {
    private int value;
    private TextureRegion graphic;
    private Random random;

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
                double y = 4.5f - (3.5f * Math.sin(Math.toRadians(angulo)));
                lugarX = (float) x;
                lugarY = (float) y;
                batch.draw(graphic, lugarX, lugarY, this.getWidth(), this.getHeight());
            } else{
                double x = 4f - (3.5f * Math.cos(Math.toRadians(angulo)));
                double y = 4.5f - (3.5f * Math.sin(Math.toRadians(angulo)));
                angulo -= 2;
                lugarX = (float) x;
                lugarY = (float) y;
                batch.draw(graphic, lugarX, lugarY, this.getWidth(), this.getHeight());
            }
        }else {
            batch.draw(graphic, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    public void disparar(final FichaTablero targetCas, final FichaRotatoria throwingCas, final FichaProxima nextCas) {
        //final Vector2 posInicial = throwingCas.getPosition();
        Vector2 targetPos = targetCas.getPosition();
        ThrowAction throwAction = new ThrowAction(targetPos);
        //JoinAction joinAction;
        throwAction.setSpeed(CONSTANTES.THROW_SPEED);

        //Se posiciona y lanza la ficha, cambiando el valor del target
        //throwingCas.setPosition(lugarX, lugarY);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                targetCas.changeValue(throwingCas.value);
                return true;
            }
        });
        //  joinAction = new JoinAction(targetCas);
        //  sequenceAction.addAction(joinAction);
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
