package helper;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.pigeon.game.GameScreen;
import objects.player.Player;
import objects.player.PlayerAnimation;


import static helper.Constants.PPM;

public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setUpMap() {
//        tiledMap = new TmxMapLoader().load("./Client/assets/maps/map1.tmx");
        tiledMap = new TmxMapLoader().load("C:\\Users\\ander\\IdeaProjects\\piGeon\\Client\\assets\\maps\\map1.tmx");
        MapLayers layers = tiledMap.getLayers();
        MapLayer objects = layers.get("objects");
        parseMapObjects(objects.getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);

    }

    //parse "objects" from map. if object name is "player"- create a rectangle player object
    private void parseMapObjects(MapObjects mapObjects) {
        System.out.println("TileMapHelper parseMapObjects()");
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
            //change to tiledmaptiledmapobject

            if (mapObject instanceof TiledMapTileMapObject) {
                TiledMapTile player = ((TiledMapTileMapObject) mapObject).getTile();
                System.out.println(player.getObjects().getCount());
                Body body = BodyHelperService.createBody(
                        20 + 16 / 2,
                        20 + 16 / 2,
                        16,
                        16,
                        false,
                        gameScreen.getWorld()
                );
                PlayerAnimation pigeon = new PlayerAnimation();
                pigeon.create();
                pigeon.render();
                pigeon.dispose();
                gameScreen.setPlayer(new Player(16, 16, body));

            }

        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        System.out.println("TileMapHelper createStaticBody()");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        System.out.println("TileMapHelper createPolygonShape()");
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
            worldVertices[i] = current;
        }
        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }
}
