package io.gdxvania.entities.enemies;
import io.gdxvania.utils.Constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bat extends Enemy {
    private static final float BASE_SPEED = 80f;
    private static final float SPEED_VARIATION = 20f;
    private static final float ITEM_PROBABILITY = 0.3f;
    private static final int SCORE = 20;
    public static final float SPAWN_RATE = 0.4f;
    private float flapTimer = 0;
    private float flapInterval = 0.25f;
    private Texture[] frames;
    private int currentFrame = 0;

    public Bat(Vector2 startPos) {
        super(startPos, "bat_1.png", BASE_SPEED, SPEED_VARIATION, ITEM_PROBABILITY, SCORE, SPAWN_RATE);
        this.frames = new Texture[]{
                super.texture,
                new Texture("bat_2.png"),
                new Texture("bat_3.png")
        };
    }

    @Override
    public void update(float delta) {
        // Sin-like movement
        position.y += MathUtils.sin(position.x * 0.18f + flapTimer * 7f) * 28 * delta;

        // Horizontal movement
        updateHorizontalMovement(delta);

        // Flapping animation
        flapTimer += delta;
        if (flapTimer >= flapInterval) {
            currentFrame = (currentFrame + 1) % frames.length;
            flapTimer = 0;
        }

        // Vertical movement
        if (MathUtils.random(0f, 1f) < 0.1f) {
            velocity.y = MathUtils.random(-40f, 40f);
        } else {
            velocity.y *= 0.9f;
            if (Math.abs(velocity.y) < 5f) velocity.y = 0;
        }
        position.y += velocity.y * delta;
        position.y = MathUtils.clamp(position.y, Constants.GROUND_LEVEL + 5, Constants.SCREEN_HEIGHT - 5);
    }
    
    @Override
    public void render(SpriteBatch batch) {
    	super.render(batch, frames[currentFrame]);
    }       

    @Override
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, frames[0].getWidth(), frames[0].getHeight());
    }
}