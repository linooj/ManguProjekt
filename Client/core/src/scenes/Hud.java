package scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pigeon.game.PigeonGame;

public class Hud implements Disposable {

    // Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    // when our game world moves, we want the hud to stay locked, so we're using new camera and new viewport for our hud
    private Viewport viewport;

    // Tuvi Score/Time tracking variables
    private Integer worldTimer;
    private float timeCount;
    private Integer score;

    // Scene2D widgets
    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
//    Label levelLabel;
//    Label worldLabel;
    Label tuviLabel;

    /**
     * Hud constructor.
     *
     * @param sb - SpriteBatch for Tuvi Game.
     */
    public Hud(SpriteBatch sb) {
        // define our tracking variables
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(PigeonGame.V_WIDTH, PigeonGame.V_HEIGHT, new OrthographicCamera());
        // We can think of Stage as empty box, if we try just put widgets in there, they would fall, they would not
        // have any kind of organization. In order to provide some sort of organization, we're going to create a table
        // inside our stage. Then we can lay out that table in a way to organize our labels to certain position
        // inside our stage.
        stage = new Stage(viewport, sb);

        // we are using Scene2d table;
        Table table = new Table();
        // by default, it would align table in the center, so we are putting it to the top of our stage
        table.top();
        // This means that our table now is the size of our stage
        table.setFillParent(true);

        countdownLabel = new Label(
                String.format("%03d", worldTimer),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE)
        );
        scoreLabel = new Label(
                String.format("%06d", score),
                new Label.LabelStyle(new BitmapFont(), Color.WHITE)
        );
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tuviLabel = new Label("TUVI", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // to add our labels to the table, we are going to use .add method.
        // we are going to expand Tuvi label to length of our screen
        // when we are using .exapandX method with multiple labels, it will share equal space for every of them
        table.add(tuviLabel).expandX().padTop(10);
//        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row(); // everything below this, will be on a new row
        table.add(scoreLabel).expandX();
//        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        // adding table to our stage:
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
