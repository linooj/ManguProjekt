package objects.player;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Bread extends InteractiveTileObject {

    public Bread(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }

}
