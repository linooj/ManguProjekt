package objects.player;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerAnimation implements ApplicationListener {
    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 11, FRAME_ROWS = 5;
    private static final int PIGEON_ROW_START = 2, PIGEON_COLS_START = 1;
    private static final int PIGEON_ROW_END = 2, PIGEON_COLS_END = 3;

    Animation<TextureRegion> flap;
    Texture flapSheet;
    SpriteBatch spriteBatch;
    // A variable for tracking elapsed time for the animation
    float stateTime;

    @Override
    public void create() {

        // Load the sprite sheet as a Texture
        flapSheet = new Texture(Gdx.files.internal("C:\\Users\\lnooj\\IdeaProjects\\piGeon\\Client\\assets\\maps\\pigeonsTiles.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(flapSheet,
                flapSheet.getWidth() / FRAME_COLS,
                flapSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] flapFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = PIGEON_COLS_START;

        // choose only the first pigeon: row-1,2,3 col-2
        for (int i = 0; i <= PIGEON_ROW_END; i++) {
            for (int j = 0; j <= PIGEON_COLS_END; j++) {
                flapFrames[index++] = tmp[i][j];
                System.out.println(tmp[i][j].getRegionX() + i);
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        flap = new Animation<>(0.05f, flapFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = flap.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        flapSheet.dispose();
    }
}
