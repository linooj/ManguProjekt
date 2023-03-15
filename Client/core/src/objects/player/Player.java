package objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameEntity {

    public Player(float width, float height, Rectangle gameEntityBorder) {
        super(width, height, gameEntityBorder);
        this.position = new Vector2(200, 200);
        this.velocity = new Vector2();
        this.width = width;
        this.height = height;
        this.gameEntityBorder = gameEntityBorder;
    }


    public void update() {
        //have x and y at the center of the body
//        x = body.getPosition().x * PPM;
//        y = body.getPosition().y * PPM;
//        checkUserInput();
    }

    public void render(SpriteBatch batch) {

    }

}
