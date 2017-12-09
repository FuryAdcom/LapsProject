package com.example.jose.lapsproyect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Laps extends ApplicationAdapter {
	Tablero tablero;
	public static TextureAtlas circles;

	@Override
	public void create () {
		circles = new TextureAtlas(Gdx.files.internal("fichas.atlas"));
		OrthographicCamera cam = new OrthographicCamera();
		Viewport viewport = new FitViewport(CONSTANTES.ANCHO,CONSTANTES.LARGO,cam);
		tablero = new Tablero(viewport,9);
		Gdx.input.setInputProcessor(tablero);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		tablero.act();
		tablero.draw();
	}

	@Override
	public void resize(int width, int height) {
		tablero.getViewport().update(width,height,true);
	}
	
	@Override
	public void dispose () {
		tablero.dispose();
		circles.dispose();
	}
}
