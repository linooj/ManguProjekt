package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.pigeon.game.GameScreen;
import objects.player.Player;

import java.awt.*;

public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    //TODO setupMap function

    public OrthogonalTiledMapRenderer setUpMap() {
        tiledMap = new TmxMapLoader().load("maps/map0.tmx");
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for(MapObject mapObject : mapObjects) {

            if(mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player")){
                    Body body= BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth()/2,
                            rectangle.getY() + rectangle.getHeight()/2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld()
                    );
                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
                }
            }
        }
    }
}
