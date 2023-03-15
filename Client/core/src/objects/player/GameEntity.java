package objects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameEntity {
    protected float speed;

    protected Vector2 position;
    protected Vector2 velocity;
    protected float width, height;
    protected Rectangle gameEntityBorder;

    public GameEntity(float width, float height, Rectangle gameEntityBorder) {
        this.width = width;
        this.height = height;
        this.position = new Vector2(2000, 2000);
        this.velocity = new Vector2();
        this.speed = 0;
        this.gameEntityBorder = gameEntityBorder;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Rectangle getGameEntityBorder() {
        return gameEntityBorder;
    }

    public float getSpeed() {
        return speed;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {

    }

}
