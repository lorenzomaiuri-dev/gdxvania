package io.gdxvania.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.gdxvania.Main;

public class TutorialScreen implements Screen {
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    private final Main game;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private OrthographicCamera camera;
    private Viewport viewport;

    public TutorialScreen(Main game) {
        this.game = game;
        this.batch = game.batch;

        this.font = new BitmapFont();
        this.font.getData().setScale(1.5f);

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0, 0, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        float centerX = VIRTUAL_WIDTH / 2f;

        font.draw(batch, "TIPS", centerX, VIRTUAL_HEIGHT - 40, 0, Align.center, false);
        font.draw(batch, "Move: directional arrows", centerX, VIRTUAL_HEIGHT - 100, 0, Align.center, false);
        font.draw(batch, "Jump: SPACE BAR", centerX, VIRTUAL_HEIGHT - 140, 0, Align.center, false);
        font.draw(batch, "Whip: X", centerX, VIRTUAL_HEIGHT - 180, 0, Align.center, false);
        font.draw(batch, "Knife: C", centerX, VIRTUAL_HEIGHT - 220, 0, Align.center, false);
        font.draw(batch, "Press ENTER to hunt for Dracula", centerX, VIRTUAL_HEIGHT / 2f - 80, 0, Align.center, false);

        batch.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
        }
    }

    @Override public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        font.dispose();
    }
}
