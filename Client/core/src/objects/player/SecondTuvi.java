package objects.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.pigeon.game.GameScreen;
import com.pigeon.game.PigeonGame;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import network.ClientConnection;

public class SecondTuvi extends Sprite{

    public enum State {
        STANDING, FLYING
    }
    public Tuvi.State currentState;
    public Tuvi.State previousState;
    private ClientConnection clientConnection;



    // this is the world where Tuvi is going to live in
    private TextureRegion tuviStand;

    private Animation tuviFLy;
    private float stateTimer;

    public SecondTuvi(GameScreen screen) {
        super(screen.getAtlas().findRegion("tuvi"));
        currentState = Tuvi.State.STANDING;
        previousState = Tuvi.State.STANDING;
        stateTimer = 0;

        // create an array of TextureRegions to pass the constructor for the animation
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 3; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        tuviFLy = new Animation(0.1f, frames);
        frames.clear();

        tuviStand = new TextureRegion(getTexture(), 0, 0, 16, 16); // second pigeon picture from 3
        setBounds(0, 0, 16 / PigeonGame.PPM, 16 / PigeonGame.PPM); // vb vaja muuta
        setRegion(tuviStand);
    }

    public void update(float dt, float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
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

    //ToDo add boolean ifFlying to server info
    public Tuvi.State getState() {
//        if (b2body.getLinearVelocity().y > 0) {
//            return Tuvi.State.FLYING;
//        } else {
//            return Tuvi.State.STANDING;
//        }
        return Tuvi.State.FLYING;
    }

}
