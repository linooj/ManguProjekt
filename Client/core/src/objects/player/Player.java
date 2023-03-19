package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import network.Location;

import static helper.Constants.PPM;


public class Player extends GameEntity {
    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 10f;
    }

    @Override
    public void update() {
        //have x and y at the center of the body
//      Kui teha ilma PPMita, siis ei muutu mitte midagi
        x = body.getPosition().x;
        y = body.getPosition().y;
        checkUserInput();

    }

    @Override
    public void render(SpriteBatch batch) {

    }

//    Seda ei ole serverisse vaja
    private void checkUserInput() {
        velX = 0;
//      Vel muutmine muudab ainult KASTi kiirust (numbreid saab timmida, et ta oleks kiirem)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) velX=1 * PPM;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) velX=-1 * PPM;

        //peab korduvalt vajutama,et õhus püsida
//      Siin tuleb numbritega nussida, et see füüsika tööle hakkaks
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            float force = body.getMass() * 15 * PPM;
            body.setLinearVelocity(body.getLinearVelocity().x, 0);
            body.applyLinearImpulse(new Vector2(0, force), body.getPosition(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            float force = body.getMass() * PPM;
            //body.setLinearVelocity(0, body.getLinearVelocity().y);
            body.applyLinearImpulse(new Vector2(0, -force), body.getPosition(),true);
        }

        body.setLinearVelocity(velX*speed, body.getLinearVelocity().y);

    }

    public Location getLocation() {
        return new Location(this.getBody().getPosition().x, this.getBody().getPosition().y);
    }
}
