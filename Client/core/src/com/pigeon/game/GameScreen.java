package com.pigeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import objects.player.Player;


import java.util.List;

import static helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

//
    static float JUMP_VELOCITY = 40f;
    static float GRAVITY = -1f;

    final PigeonGame game;
    private OrthographicCamera camera;

    private SpriteBatch batch;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    //game objects
    private Player player;

//  Testing testing
    private Array<Rectangle> tiles = new Array<Rectangle>();

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    /**
     * GameScreen constructor.
     * */
    public GameScreen(OrthographicCamera camera, final PigeonGame game) {
        this.game = game;
        this.camera = camera;

        tiledMap = new TmxMapLoader().load("./Client/assets/maps/map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

//      Algsesse kohta joonistamine
        Rectangle gameEntityBorder = new Rectangle();
        this.player = new Player(4f, 4f, gameEntityBorder);

        this.batch = new SpriteBatch();
//        batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void render(float delta) {

//        camera.update();
//      See peab jääma muidu framed kirjutavad üle üksteist
        Gdx.gl.glClearColor(0, 0, 0, 1); //black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();

        checkUserInput();
        //render objects
        Texture pigeonTexture = new Texture(Gdx.files.internal("C:\\Users\\ander\\IdeaProjects\\piGeon\\Client\\assets\\characters\\pigeon_flap_1.png"));
        batch.draw(pigeonTexture, player.getPosition().x, player.getPosition().y, 40, 40);
        player.getPosition().add(0, GRAVITY);

        batch.end();


//        update();

    }

    private void collisionDetection() {



    }


    private void update() {

        cameraUpdate();
        checkUserInput();
        tiledMapRenderer.setView(camera);
        player.update();

        System.out.println("update on kasutuses");

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        //get player positions x and y value, bring it to real world position and round it for smoother camera movement
        position.x = Math.round(player.getPosition().x * PPM * 10) / 10f;
        position.y = Math.round(player.getPosition().x * PPM * 10) / 10f;

        camera.position.set(position);
        camera.update();
    }



    private void checkUserInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.getPosition().add(1, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.getPosition().add(-1, 0);

        //peab korduvalt vajutama,et õhus püsida
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
//            player.setVelocity(0, player.getVelY() + JUMP_VELOCITY);
            player.getPosition().add(0, JUMP_VELOCITY);
//            player.getVelocity().add(0, GRAVITY);
//            player.getPosition().add(0, player.getVelocity().y);
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            float force = body.getMass();
//            //body.setLinearVelocity(0, body.getLinearVelocity().y);
//            player.setVelocity(0, player.getVelY() + JUMP_VELOCITY);
//            body.applyLinearImpulse(new Vector2(0, -force), body.getPosition(),true);
//        }
//
//        body.setLinearVelocity(velX*speed, body.getLinearVelocity().y);


        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("objects");
        System.out.println(layer.getTileHeight());
        Array<RectangleMapObject> objects = layer.getObjects().getByType(RectangleMapObject.class);

        System.out.println(objects.size);
        for (int i = 0; i < objects.size; i++) {
            if (player.getGameEntityBorder().overlaps(objects.get(i).getRectangle())) {
                System.out.println("hallloooooooooooo");
                player.getPosition().set(player.getPosition().x, 16);
            }
        }


//        Rectangle pigeonRectangle = rectPool.obtain();
//        pigeonRectangle.set(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());
//        int startX, startY, endX, endY;
//        if (player.getVelocity().x > 0) {
//            startX = endX = (int)(player.getPosition().x + player.getWidth() + player.getVelocity().x);
//        } else {
//            startX = endX = (int)(player.getPosition().x + player.getVelocity().x);
//        }
//
//
//        startY = (int)(player.getPosition().y);
//        endY = (int)(player.getPosition().y + player.getHeight());
//        getTiles(startX, startY, endX, endY, tiles);
//        pigeonRectangle.x += player.getVelocity().x;
//        for (Rectangle tile : tiles) {
//            if (pigeonRectangle.overlaps(tile)) {
//                player.getVelocity().x = 0;
//                break;
//            }
//        }
//        pigeonRectangle.x = player.getPosition().x;
//
//        // if the koala is moving upwards, check the tiles to the top of its
//        // top bounding box edge, otherwise check the ones to the bottom
//        if (player.getVelocity().y > 0) {
//            startY = endY = (int)(player.getPosition().y + player.getHeight() + player.getVelocity().y);
//        } else {
//            startY = endY = (int)(player.getPosition().y + player.getVelocity().y);
//        }
//        startX = (int)(player.getPosition().x);
//        endX = (int)(player.getPosition().x + player.getWidth());
//        getTiles(startX, startY, endX, endY, tiles);
//        pigeonRectangle.y += player.getVelocity().y;
//
//        for (Rectangle tile : tiles) {
//            System.out.println(pigeonRectangle.overlaps(tile));
//            if (pigeonRectangle.overlaps(tile)) {
//                // we actually reset the koala y-position here
//                // so it is just below/above the tile we collided with
//                // this removes bouncing :)
//                if (player.getVelocity().y > 0) {
//                    player.getPosition().y = tile.y - player.getHeight();
//                    // we hit a block jumping upwards, let's destroy it!
//                    TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get("Tile Layer");
//                    layer.setCell((int)tile.x, (int)tile.y, null);
//                } else {
//                    player.getPosition().y = tile.y + tile.height;
//                    // if we hit the ground, mark us as grounded so we can jump
////                    player.grounded = true;
//                }
//                player.getVelocity().y = 0;
//                break;
//            }
//        }
//        rectPool.free(pigeonRectangle);
//
//        // unscale the velocity by the inverse delta time and set
//        // the latest position
//        player.getPosition().add(player.getVelocity());
//
////        player.getVelocity().scl(1 / deltaTime);
//
//        // Apply damping to the velocity on the x-axis so we don't
//        // walk infinitely once a key was pressed
//
////        player.getVelocity().x *= Koala.DAMPING;
        
        player.getGameEntityBorder().set(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());

    }

    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer");
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
//            System.out.println("loopidi loop");
            for (int x = startX; x <= endX; x++) {
//                System.out.println("skoobidi skoop");
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                } else {
                    System.out.println(x + " " + y);
                }
            }
        }
    }

//    public void setPlayer(Player player) {
//        this.player = player;
//    }
}
