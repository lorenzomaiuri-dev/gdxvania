package io.gdxvania.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Powerup {
    private Vector2 position;
    private Texture texture = new Texture("powerup.png");

    public Powerup(Vector2 spawnPosition) {
        this.position = new Vector2(spawnPosition);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void dispose() {
        texture.dispose();
    }
}
