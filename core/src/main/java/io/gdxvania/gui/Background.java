package io.gdxvania.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Background {
    private Texture backgroundTexture;
    private TextureRegion backgroundRegion;
    private Viewport viewport;

    public Background(String texturePath, float worldWidth, float worldHeight) {
        backgroundTexture = new Texture(Gdx.files.internal(texturePath));
        backgroundRegion = new TextureRegion(backgroundTexture);
        viewport = new FitViewport(worldWidth, worldHeight);
        viewport.apply();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.draw(backgroundRegion, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
    }

    public void dispose() {
        backgroundTexture.dispose();
    }

    public Viewport getViewport() {
        return viewport;
    }
}