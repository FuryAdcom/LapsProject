package com.example.jose.lapsproyect;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Jose on 1/6/2018.
 */

public class TableroAux  extends Stage {
    CasillaAux casilla;

    public TableroAux(Viewport viewport, int dimension_casilla) {
        super(viewport);
        casilla = new CasillaAux(dimension_casilla);
        casilla.addToStage(this);
    }
}
