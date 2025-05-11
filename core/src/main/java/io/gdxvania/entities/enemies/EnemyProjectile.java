package io.gdxvania.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.gdxvania.utils.Constants;

public abstract class EnemyProjectile {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Texture texture;
    protected Rectangle bounds;
    protected int damage;

    public EnemyProjectile(Vector2 startPos, Vector2 velocity, Texture texture, int damage) {
        this.position = startPos;
        this.velocity = velocity;
        this.texture = texture;
        this.bounds = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
        this.damage = damage;
    }

    public abstract void update(float delta);
    public void render(SpriteBatch batch) {
    	TextureRegion region = new TextureRegion(texture);
        batch.draw(region,
                   position.x, position.y, // position
                   0, 0, // originX, originY
                   texture.getWidth(), texture.getHeight(), // width, height
                   (velocity.x > 0 ? -1 : 1), 1, // scaleX, scaleY
                   0); // rotation
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isOffScreen() {
        return position.x < -texture.getWidth() || position.x > Constants.SCREEN_WIDTH || 
               position.y < -texture.getHeight() || position.y > Constants.SCREEN_HEIGHT; 
    }

    public int getDamage() {
        return damage;
    }
}