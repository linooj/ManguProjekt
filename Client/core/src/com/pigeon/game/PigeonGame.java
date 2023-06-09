package com.pigeon.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pigeon.game.GameScreen;

import java.io.IOException;

public class PigeonGame extends Game {
    // these are virtual width and height for our game
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    // SpriteBatch is a container that holds all of our images, textures and stuff like that
    public SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        try {
            setScreen(new GameScreen(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render () {
        // delegate render method tot the PlayScreen or whatever screen that is active at that time
        super.render();
    }

}
