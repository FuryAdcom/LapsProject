package com.example.jose.lapsproyect;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import java.util.ArrayList;

/**
 * Created by Adrian on 12/9/2017.
 */

public class JoinAction extends MoveToAction {
    private ArrayList<FichaTablero> fichas;
    private float Speed;
    private double angulo = 90;

    public JoinAction(FichaTablero targetCas) {
        super();
        this.getResto(targetCas ,targetCas, null, null);
    }

    private void getResto(FichaTablero ficha, FichaTablero inicial, FichaTablero siguiente, FichaTablero anterior) {
        if(siguiente != inicial && siguiente != anterior){

        }
    }

    private float calculateTime(float speed) {
        Actor actor = getActor();
        if (actor != null) {
            float targetX = getX();
            float targetY = getY();
            float currentX = actor.getX();
            float currentY = actor.getY();
            //Armamos el vector distancia con los puntos current y target
            Vector2 distancia = new Vector2(currentX - targetX, currentY - targetY);
            //Modulo del vector distancia
            float distancia_mod = distancia.len();
            //Calculamos el tiempo necesario para mantener la velocidad constante usando
            //la formula V = d/t
            return distancia_mod / speed;
        }
        return 0f;
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        float dur = calculateTime(Speed);
        this.setDuration(dur);
    }

    public void setSpeed(float speed) {
        this.Speed = speed;
    }
}
