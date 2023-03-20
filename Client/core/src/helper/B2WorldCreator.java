package helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pigeon.game.PigeonGame;
import objects.player.Bread;

public class B2WorldCreator {

    public B2WorldCreator(World world, TiledMap map) {
        // define a body
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        // define a fixture
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground bodies/fixtures:

        // We are going to create body and a fixture in every corresponding object in our Tiled map layers
        // in for-loop at .get(2) we are taking the pillars layer
        // (it's 2 because it's third layer from the bottom in Tiled app - start from 0, 1, 2)
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            // let's get rectangle object itself
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            // let's define our body:
            // [There's 3 types of bodies, that can happen in Box2D: DynamicBody, KinematicBody, StaticBody
            // DynamicBody is something which would be affected by forces, gravity and all other stuff (player)
            // StaticBody - they don't move. You can move them, forcefully by programming, but otherwise they aren't
            // affected by any forces and stuff
            // KinematicBody - they are affected by forces like gravity, but they can't be manipulated with velocity
            // (for example good for pendulums, moving platforms and stuff like that)]
            bdef.type = BodyDef.BodyType.StaticBody;
            // let's set the position:
            bdef.position.set(
                    (rect.getX() + rect.getWidth() / 2) / PigeonGame.PPM,
                    ((rect.getY() + rect.getHeight() / 2)) / PigeonGame.PPM
            );

            // Let's add a body to our Box2D world:
            body = world.createBody(bdef);

            // Let's work on our fixture:
            // We need to set our shape
            shape.setAsBox(
                    (rect.getWidth() / 2) / PigeonGame.PPM,
                    (rect.getHeight() / 2) / PigeonGame.PPM
            );
            fdef.shape = shape;
            // now we can add fixture to our body:
            body.createFixture(fdef);
        }

        // Create pillars' bodies/fixtures

        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(
                    (rect.getX() + rect.getWidth() / 2) / PigeonGame.PPM,
                    ((rect.getY() + rect.getHeight() / 2)) / PigeonGame.PPM
            );

            body = world.createBody(bdef);

            shape.setAsBox(
                    (rect.getWidth() / 2) / PigeonGame.PPM,
                    (rect.getHeight() / 2) / PigeonGame.PPM
            );
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // Create bread bodies/fixtures

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Bread(world, map, rect);
        }
    }

}
