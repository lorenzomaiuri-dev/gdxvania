package io.gdxvania.gui.screens;

import com.badlogic.gdx.Screen;

import io.gdxvania.GameManager;
import io.gdxvania.Main;

public class GameScreen implements Screen {
    private final Main game;
    private final GameManager gameManager;

    public GameScreen(Main game) {
        this.game = game;
        this.gameManager = GameManager.getInstance(game.batch);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        gameManager.update();
        gameManager.render();

        if (gameManager.getIsGameOver()) {
        	game.setScreen(new GameOverScreen(game));
        } else if (gameManager.getIsVictory()) {
        	game.setScreen(new VictoryScreen(game));
        }
    }

    @Override public void resize(int width, int height) {
        gameManager.resize(width, height);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        gameManager.dispose();
    }
}
