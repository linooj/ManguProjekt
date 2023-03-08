package com.pigeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import network.ClientConnection;
import network.Location;
import objects.player.Player;

import java.io.IOException;

import static helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    //
    private SpriteBatch batch;
    // world's class stores box2D bodies
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    //game objects
    private Player player;
    private ClientConnection clientConnection;

    /**
     * GameScreen constructor.
     * */
    public GameScreen(OrthographicCamera camera) throws IOException {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.tileMapHelper = new TileMapHelper(this);
        this.orthogonalTiledMapRenderer = tileMapHelper.setUpMap();
        this.clientConnection = new ClientConnection();
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();
        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

//      For testing purposes
//        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) clientConnection.client.sendUDP("not pressed");
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) clientConnection.client.sendUDP("right");
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) clientConnection.client.sendUDP("left");
//        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) clientConnection.client.sendUDP("up");
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) clientConnection.client.sendUDP("down");

        clientConnection.client.sendUDP(player.getLocation());

    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        //get player positions x and y value, bring it to real world position and round it for smoother camera movement
        position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
        position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
        camera.position.set(position);
        camera.update();
    }


    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0, 0, 0, 1); //black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();
        //render objects

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
