//package helper;
//
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.maps.MapLayer;
//import com.badlogic.gdx.maps.MapLayers;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.MapObjects;
//import com.badlogic.gdx.maps.objects.PolygonMapObject;
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTile;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
//import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
//import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.*;
//import com.pigeon.game.GameScreen;
//import objects.player.Player;
//import objects.player.PlayerAnimation;
//
//
//import static helper.Constants.PPM;
//
//public class TileMapHelper {
//
//    private TiledMap tiledMap;
//    private GameScreen gameScreen;
//
//    public TileMapHelper(GameScreen gameScreen) {
//        this.gameScreen = gameScreen;
//    }
//
//    public OrthogonalTiledMapRenderer setUpMap() {
////        tiledMap = new TmxMapLoader().load("./Client/assets/maps/map1.tmx");
//        tiledMap = new TmxMapLoader().load("C:\\Users\\ander\\IdeaProjects\\piGeon\\Client\\assets\\maps\\map1.tmx");
//        MapLayers layers = tiledMap.getLayers();
//        MapLayer objects = layers.get("objects");
//        parseMapObjects(objects.getObjects());
//        return new OrthogonalTiledMapRenderer(tiledMap);
//
//    }
//
//    //parse "objects" from map. if object name is "player"- create a rectangle player object
//    private void parseMapObjects(MapObjects mapObjects) {
//        System.out.println("TileMapHelper parseMapObjects()");
//        for (MapObject mapObject : mapObjects) {
//
//            if (mapObject instanceof PolygonMapObject) {
//                createStaticBody((PolygonMapObject) mapObject);
//            }
//            //change to tiledmaptiledmapobject
//
//            if (mapObject instanceof TiledMapTileMapObject) {
////                TiledMapTile player = ((TiledMapTileMapObject) mapObject).getTile();
////                System.out.println(player.getObjects().getCount());
//                Body body = createBody(
//                        20 + 16 / 2,
//                        20 + 16 / 2,
//                        16,
//                        16,
//                        false,
//                        gameScreen.getWorld()
//                );
//                PlayerAnimation pigeon = new PlayerAnimation();
//                pigeon.create();
//                pigeon.render();
//                pigeon.dispose();
//                gameScreen.setPlayer(new Player(16, 16, body));
//
//            }
//
//        }
//    }
//
////  Need 2 alumist funktsiooni loovad kaardi
//    private void createStaticBody(PolygonMapObject polygonMapObject) {
//        System.out.println("TileMapHelper createStaticBody()");
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyDef.BodyType.StaticBody;
//        Body body = gameScreen.getWorld().createBody(bodyDef);
//        Shape shape = createPolygonShape(polygonMapObject);
//        body.createFixture(shape, 1000);
//        shape.dispose();
//    }
//
//    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
//        System.out.println("TileMapHelper createPolygonShape()");
//        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
//        Vector2[] worldVertices = new Vector2[vertices.length / 2];
//
//        for (int i = 0; i < vertices.length / 2; i++) {
//            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
//            worldVertices[i] = current;
//        }
//        PolygonShape shape = new PolygonShape();
//        shape.set(worldVertices);
//        return shape;
//    }
//
//    public static Body createBody(
//            float x,
//            float y,
//            float width,
//            float height,
//            boolean isStatic,
//            World world
//    ) {
//        BodyDef bodyDef = new BodyDef();
//        //if boolean isStatic is true -> creates static body, else ...
//        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
//        bodyDef.position.set(x / PPM, y / PPM);
//        bodyDef.fixedRotation = true;
//        Body body = world.createBody(bodyDef);
//
//        //define shape
//        PolygonShape shape = new PolygonShape();
////      Vb halb ja paha
//        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = shape;
//        body.createFixture(fixtureDef);
//        shape.dispose();
//        System.out.println("BodyHelperService createBody()");
//        return body;
//    }
//
//}
package com.pigeon.helper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class TileMapHelper extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite sprite;
    Texture img;
    World world;
    Body body;

    @Override
    public void create() {

        batch = new SpriteBatch();
        // We will use the default LibGdx logo for this example, but we need a
//        sprite since it's going to move
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);

        // Center the sprite in the top/middle of the screen
        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);

        // Create a physics world, the heart of the simulation.  The Vector
//        passed in is gravity
        world = new World(new Vector2(0, -98f), true);

        // Now create a BodyDefinition.  This defines the physics objects type
//        and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine
//        is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set(sprite.getX(), sprite.getY());

        // Create a body in the world using our definition
        body = world.createBody(bodyDef);

        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        // We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensions
//        as our sprite
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the
        // body
        // you also define it's properties like density, restitution and others
        // we will see shortly
        // If you are wondering, density and area are used to calculate over all
        // mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
    }

    @Override
    public void render() {

        // Advance the world, by the amount of time that has elapsed since the
//        last frame
        // Generally in a real game, dont do this in the render loop, as you are
//        tying the physics
        // update rate to the frame rate, and vice versa
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // Now update the sprite position accordingly to it's now updated
//        Physics body
        sprite.setPosition(body.getPosition().x, body.getPosition().y);

        // You know the rest...
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();
    }

    @Override
    public void dispose() {
        // Hey, I actually did some clean up in a code sample!
        img.dispose();
        world.dispose();
    }
}