package com.example.jose.lapsproyect;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Toolkit;

public class Laps extends ApplicationAdapter {
	Tablero tablero;
	public static TextureAtlas circles;
	public static Texture back;
	private SpriteBatch batch;

    public FreeTypeFontGenerator generadorScore;
    public BitmapFont score;
    public FreeTypeFontGenerator generadorAS;
    public  BitmapFont actualizarScore;

	@Override
	public void create () {
		circles = new TextureAtlas(Gdx.files.internal("fichas.atlas"));
		back = new Texture(Gdx.files.internal("Back.png"));
        generadorScore = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AbcariCircle Wide.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametroScore = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametroScore.size = 40;
        score = generadorScore.generateFont(parametroScore);

        generadorAS = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Aftershock Debris CondSolid.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parametroAS = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parametroAS.size = 30;
        actualizarScore = generadorAS.generateFont(parametroAS);
		batch = new SpriteBatch();

		OrthographicCamera cam = new OrthographicCamera();
		Viewport viewport = new FitViewport(CONSTANTES.ANCHO,CONSTANTES.LARGO,cam);
		tablero = new Tablero(viewport,8);
		Gdx.input.setInputProcessor(tablero);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(back, 0 , 0, Gdx.graphics.getWidth()+1, Gdx.graphics.getHeight()+1);
        score.draw(batch, "SCORE", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2+Gdx.graphics.getHeight()/4+20);
        actualizarScore.draw(batch, Integer.toString(tablero.score), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2+Gdx.graphics.getHeight()/4-40);
        actualizarScore.getData().setScale(2, 1);
        batch.end();
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
		back.dispose();
		score.dispose();
	}
}
