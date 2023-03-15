package com.pigeon.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PigeonGame extends Game {

    public static PigeonGame INSTANCE;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public SpriteBatch batch;
    //for text on screen
    public BitmapFont font;


    @Override
    public void create() {
//        batch = new SpriteBatch();
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
        setScreen(new GameScreen(orthographicCamera, INSTANCE));
    }

    public void render() {
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
