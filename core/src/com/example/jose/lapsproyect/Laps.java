package com.example.jose.lapsproyect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

public class Laps extends ApplicationAdapter {
	TableroAux tableroAux;
	Tablero tablero;
	public static TextureAtlas circles;
	public static Texture back;
	private SpriteBatch batch;


	@Override
	public void create () {
		circles = new TextureAtlas(Gdx.files.internal("fichas.atlas"));
		back = new Texture(Gdx.files.internal("Back.png"));
		batch = new SpriteBatch();

		OrthographicCamera cam = new OrthographicCamera();
		Viewport viewport = new FitViewport(CONSTANTES.ANCHO,CONSTANTES.LARGO,cam);
		tableroAux = new TableroAux(viewport,8);
		tablero = new Tablero(viewport,8);
		Gdx.input.setInputProcessor(tablero);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(back, 0 , 0, Gdx.graphics.getWidth()+1, Gdx.graphics.getHeight()+1);
		batch.end();
		tableroAux.draw();
		tablero.act();
		tablero.draw();
	}

	@Override
	public void resize(int width, int height) {
		tablero.getViewport().update(width,height,true);
	}
	
	@Override
	public void dispose () {
		tableroAux.dispose();
		tablero.dispose();
		circles.dispose();
		back.dispose();
	}
}
