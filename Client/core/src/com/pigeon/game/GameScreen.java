package com.pigeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helper.B2WorldCreator;
import objects.player.Tuvi;
import scenes.Hud;

public class GameScreen implements Screen {

    // Reference to our Game, used to set Screens
    private PigeonGame game;
    private TextureAtlas atlas;

    // basic playscreen variables
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr; //gives a graphical representation of our fixtures & bodies inside our Box2D world

    // sprites
    private Tuvi player;
    private Box2DDebugRenderer b2dr; //gives a graphical representation of our fixtures & bodies inside our Box2D world

    // Network related variables
    private ClientConnection clientConnection;


    /**
     * PlayScreen constructor.
     *
     * @param game - Tuvi game.
     */
    public GameScreen(PigeonGame game) {
        atlas = new TextureAtlas("maps/new_stuff/tuvi.txt");

        // Starting client connection
        this.clientConnection = new ClientConnection();

        this.game = game;
        // create cam used to follow pigeon through cam world
        gameCam = new OrthographicCamera();

        // create FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(
                PigeonGame.V_WIDTH / PigeonGame.PPM,
                PigeonGame.V_HEIGHT / PigeonGame.PPM,
                gameCam
        );
        // StretchVP allow to change size of screen, but graphic will be distorted
        // ScreenVP gives advantages to the players with bigger screen - bigger screen -> seeing more of map
        // FitVP will add bars depending on how big screen player has, always maintains aspect ratio

        // create our game HUD for scores/timers info
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/new_stuff/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PigeonGame.PPM);

        // initially set our gamecam to be centered correctly at the start of the map
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Vector() takes in gravity parameters
        world = new World(new Vector2(0, -6), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        // create Tuvi in our game world
        player = new Tuvi (world, this);

    }
     /**
     * GameScreen constructor.
     *
     * @param game - Tuvi game.
     */

    public GameScreen(PigeonGame game) throws IOException {
        atlas = new TextureAtlas("maps/new_stuff/tuvi.txt");

//      Starting client connection
        this.clientConnection = new ClientConnection();

        this.game = game;
        // create cam used to follow pigeon through cam world
        gameCam = new OrthographicCamera();

        // create FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(
                PigeonGame.V_WIDTH / PigeonGame.PPM,
                PigeonGame.V_HEIGHT / PigeonGame.PPM,
                gameCam
        );
        // StretchVP allow to change size of screen, but graphic will be distorted
        // ScreenVP gives advantages to the players with bigger screen - bigger screen -> seeing more of map
        // FitVP will add bars depending on how big screen player has, always maintains aspect ratio

        // create our game HUD for scores/timers info
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/new_stuff/map1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PigeonGame.PPM);

        // initially set our gamecam to be centered correctly at the start of the map
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        // Vector() takes in gravity parameters
        world = new World(new Vector2(0, -6), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        // create Tuvi in our game world
        player = new Tuvi (world, this);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    /**
     * Handles the key pressing logic
     * @param dt- delta time.
     */
    public void handleInput(float dt) {
        // check if up arrow button is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 2f), player.b2body.getWorldCenter(), true);
        }
        // check if right arrow button is pressed (is it held down)
        // and check if tuvi is not moving faster than specific speed (second check parameter in .isKeyPressed aster &&)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.05f, 0), player.b2body.getWorldCenter(), true);
        }
        // check if left arrow button is pressed (held down)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), player.b2body.getWorldCenter(), true);
        }
        // check if down arrow button is pressed (???) - vaata hiljem, kas on vaja teha seda
    }


    /**
     * all updating in the game world
     * @param dt- delta time.
     */
    public void update(float dt) {
        // handle user's input first
        handleInput(dt);

        // in order for Box2D to execute our physics simulation we need to tell how many times to calculate per second:
        world.step(1 / 60f, 6, 2);

        player.update(dt);

        // everytime the Tuvi moves we want to track it with our gamecam
        gameCam.position.x = player.b2body.getPosition().x;

        // update our gamecam with correct coordinates after changes
        gameCam.update();
        // tell our renderer to draw only what our camera can see in our game world
        renderer.setView(gameCam);

        // send UDP packets
        clientConnection.client.sendUDP(player.getLocation());

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void render(float delta) {
        // separate our update logic from render
        update(delta);

        // clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render our game map
        renderer.render();

        // render our Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        // set our batch to now draw what hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // update our game viewport
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
