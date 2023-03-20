package com.pigeon.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import network.ClientConnection;
import objects.player.Player;

import java.io.IOException;

//import static helper.Constants.PPM;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    //
    private SpriteBatch batch;
//  Parast on see pigeon
    private Sprite sprite;

    // world's class stores box2D bodies
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private TiledMap tiledMap;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    //game objects
    private Player player;
    private ClientConnection clientConnection;
    private TextureAtlas pigeonAtlas;
    private Body body;

    float stateTime;


    /**
     * GameScreen constructor.
     * */
    public GameScreen(OrthographicCamera camera) throws IOException {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        this.orthogonalTiledMapRenderer = setUpMap();
        this.clientConnection = new ClientConnection();

    }

    public OrthogonalTiledMapRenderer setUpMap() {
//        tiledMap = new TmxMapLoader().load("./Client/assets/maps/map1.tmx");
        tiledMap = new TmxMapLoader().load("maps/map1.tmx");
        MapLayers layers = tiledMap.getLayers();
        MapLayer objects = layers.get("objects");
        parseMapObjects(objects.getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);

    }

    //  Testimine
//parse "objects" from map. if object name is "player"- create a rectangle player object
    private void parseMapObjects(MapObjects mapObjects) {
        System.out.println("TileMapHelper parseMapObjects()");
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
            //change to tiledmaptiledmapobject

            if (mapObject instanceof TiledMapTileMapObject) {
//                TiledMapTile player = ((TiledMapTileMapObject) mapObject).getTile();
//                System.out.println(player.getObjects().getCount());
                pigeonAtlas = new TextureAtlas("maps/pigeonTiles3birds.txt");
                sprite = pigeonAtlas.createSprite("pigeonsTiles-10");
//              Kuubiku algus koht - timmitav
                sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, Gdx.graphics.getHeight() / 2);
                createBody(
//                        20 + 16 / 2, oli x
//                        20 + 16 / 2, oli y
//                      SEE PPM SIIN PANEB KASTI PIGEONIGA SAMASSE ALGUS KOHTA
//                        sprite.getX() / PPM,
//                        sprite.getY() / PPM,
                        sprite.getX(),
                        sprite.getY(),
                        sprite.getWidth()/2,
                        sprite.getHeight()/2,
//                        16,
//                        16,
                        false,
                        this.getWorld()
                );
                stateTime = 0f;
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
                stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

                // Get current frame of animation for the current stateTime
                //      TextureRegion currentFrame = flap.getKeyFrame(stateTime, true);


//                batch.begin();
//                //      See tegelt vajalik
//                sprite.draw(batch);
//                //      spriteBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
//                batch.end();

//                    PlayerAnimation pigeon = new PlayerAnimation();
//                    pigeon.create();
//                    pigeon.render();
//                    pigeon.dispose();

//              Selle numbri muutmine ei tundu midagi muutvat
                this.setPlayer(new Player(16, 16, body));

            }

        }
    }

//  See tegeleb kujundi mitte PIGEONI loomisega
    public void createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        //if boolean isStatic is true -> creates static body, else ...
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

//      Kui PPM ära võtad, siis ei leia enam kaarti ülesse KASTIga
//        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.position.set(x, y);

        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        //define shape
        PolygonShape shape = new PolygonShape();

//      See mõjutab KASTi suurust (kui PPMi tagant ära võtad saad massive kasti)
//        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }


    //  Need 2 alumist funktsiooni loovad kaardi
    private void createStaticBody(PolygonMapObject polygonMapObject) {
        System.out.println("TileMapHelper createStaticBody()");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = this.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        System.out.println("TileMapHelper createPolygonShape()");
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
//            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            Vector2 current = new Vector2(vertices[i * 2], vertices[i * 2 + 1]);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
//  Maailma on nende 2 funktsiooniga nüüd loodud

    private void update() {
//        linnukesega ei muutu midagi kui on kas Gdx.graphics.getDeltaTime() või 1 / 60f
//        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        world.step(1 / 60f, 6, 2);

        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        player.update();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        clientConnection.client.sendUDP(player.getLocation());

    }

    private void cameraUpdate() {
        Vector3 position = camera.position;
        //get player positions x and y value, bring it to real world position and round it for smoother camera movement
//      Kui nende numbritega nussid siis ei tööta mitte midagi

//      Need numbrid jälgivad kaameraga KASTI liikumist
//        position.x = Math.round(player.getBody().getPosition().x * PPM * 10) / 10f;
//        position.y = Math.round(player.getBody().getPosition().y * PPM * 10) / 10f;
//      Need numbrid jälgivad kaameraga PIGEONI liikumist
        position.x = player.getBody().getPosition().x;
        position.y = player.getBody().getPosition().y;
        camera.position.set(position);
        camera.update();
    }


    @Override
    public void render(float delta) {
        this.update();


        Gdx.gl.glClearColor(0, 0, 0, 1); //black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sprite.setPosition(player.getBody().getPosition().x, player.getBody().getPosition().y);
        orthogonalTiledMapRenderer.render();


        batch.begin();
        //render objects
        System.out.println(sprite.getX() + " linnukese koord " + sprite.getY());
        System.out.println(body.getPosition().x + " body joody koord " + body.getPosition().y);
        System.out.println(player.getBody().getPosition().x + " player koord " + player.getBody().getPosition().y);

//      Koordinaatide sisendi muutimine on pointless kuna need annavad nkn samu tulemusi
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();

//        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void dispose() {
        // Hey, I actually did some clean up in a code sample!
        pigeonAtlas.dispose();
        world.dispose();
    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
