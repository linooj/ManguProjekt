package com.pigeon.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import network.ClientConnection;

import java.io.IOException;


public class PigeonGame extends Game {

    public static PigeonGame INSTANCE;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;


    @Override
//  See on iseenesest mõistetav. Luuakse kaart vastavalt määratud suurusele ning määratakse kaamera algne asukoht.
    public void create() {
        System.out.println("PigeonGame create()");
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
        try {
            setScreen(new GameScreen(orthographicCamera));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
