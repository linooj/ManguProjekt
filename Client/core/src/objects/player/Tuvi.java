package objects.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pigeon.game.GameScreen;
import com.pigeon.game.PigeonGame;
import network.Location;

public class Tuvi extends Sprite {

    public enum State {
        STANDING, FLYING
    }
    public State currentState;
    public State previousState;


    // this is the world where Tuvi is going to live in
    public World world;
    public Body b2body;
    private TextureRegion tuviStand;

    private Animation tuviFLy;
    private float stateTimer;

    /**
     * Constructor
     * @param world World.
     * @param screen GameScreen.
     */
    public Tuvi(World world, GameScreen screen) {
        super(screen.getAtlas().findRegion("tuvi"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        // create an array of TextureRegions to pass the constructor for the animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        tuviFLy = new Animation(0.1f, frames);
        frames.clear();


        defineTuvi();
        tuviStand = new TextureRegion(getTexture(), 0, 0, 16, 16); // second pigeon picture from 3
        setBounds(0, 0, 16 / PigeonGame.PPM, 16 / PigeonGame.PPM); // vb vaja muuta
        setRegion(tuviStand);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case FLYING:
                region = (TextureRegion) tuviFLy.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = tuviStand;
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0) {
            return State.FLYING;
        } else {
            return State.STANDING;
        }
    }

    public void defineTuvi() {
        BodyDef bdef = new BodyDef();
        // put Tuvi on a first pillar
        bdef.position.set(16 / PigeonGame.PPM, 56 / PigeonGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PigeonGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }

    public Location getLocation() {
        return new Location(this.b2body.getPosition().x, this.b2body.getPosition().y);
    }
}
