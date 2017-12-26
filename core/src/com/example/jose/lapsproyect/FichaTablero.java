package com.example.jose.lapsproyect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;

import javax.xml.bind.Unmarshaller;

/**
 * Created by Adrian on 12/8/2017.
 */

public class FichaTablero extends Actor {
    protected int value;
    private TextureRegion graphic;

    public FichaTablero(float x, float y, int value, float lado) {
        this.value = value;

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

}
