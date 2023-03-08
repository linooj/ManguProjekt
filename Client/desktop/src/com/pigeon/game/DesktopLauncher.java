package com.pigeon.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import network.ClientConnection;

import java.io.IOException;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);

        //config.setIdleFPS(60);
        //config.useVsync(true);

//		config.setWindowedMode(960,640);
        config.setTitle("pigeon-game");
        new Lwjgl3Application(new PigeonGame(), config);
    }
}
