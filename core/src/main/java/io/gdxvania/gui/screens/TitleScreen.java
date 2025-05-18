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
import io.gdxvania.SoundManager;
import io.gdxvania.utils.Constants;

public class TitleScreen implements Screen {

    private final Main game;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private OrthographicCamera camera;
    private Viewport viewport;

    public TitleScreen(Main game) {
        this.game = game;
        this.batch = game.batch;
        this.font = new BitmapFont();
        this.font.getData().setScale(2f);

        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, camera);
        viewport.apply();
        camera.position.set(Constants.SCREEN_WIDTH / 2f, Constants.SCREEN_HEIGHT / 2f, 0);
        camera.update();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            SoundManager.getInstance().unlockAudio();
            game.setScreen(new MenuScreen(game));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "GDXVANIA", Constants.SCREEN_WIDTH / 2f, Constants.SCREEN_HEIGHT / 2f + 60, 0, Align.center, false);
        font.draw(batch, "Press ENTER to start", Constants.SCREEN_WIDTH / 2f, Constants.SCREEN_HEIGHT / 2f - 20, 0, Align.center, false);
        batch.end();
    }

    @Override public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
    }
}
