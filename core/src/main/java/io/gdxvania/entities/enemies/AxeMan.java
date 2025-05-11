package io.gdxvania.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import io.gdxvania.entities.GameEntities;

public class AxeMan extends Enemy {
    private static final float BASE_SPEED = 40;
    private static final float SPEED_VARIATION = 0;     
    private static final float ITEM_PROBABILITY = 1f;
    private static final int SCORE = 50;
    public static final float SPAWN_RATE = 0.1f;
    private static final float AXE_SPEED = 150;
    private static final float ATTACK_INTERVAL = 2f;
    private float attackTimer = 0;
    private Texture axeTexture;

    public AxeMan(Vector2 startPos) {
        super(startPos, "axeman.png", BASE_SPEED, SPEED_VARIATION, ITEM_PROBABILITY, SCORE, SPAWN_RATE);
        this.axeTexture = new Texture("axe.png");
    }

    @Override
    public void update(float delta) {
    	updateHorizontalMovement(delta);

        attackTimer += delta;
        if (attackTimer >= ATTACK_INTERVAL) {
            attack();
            attackTimer = 0;
        }
    }

    private void attack() {
        Vector2 axeStartPosition = new Vector2(position.x + (velocity.x > 0 ? getWidth() : 0), position.y + getHeight() / 2);
        Vector2 axeVelocity = new Vector2(velocity.x > 0 ? AXE_SPEED : -AXE_SPEED, 0);
        GameEntities gameEntities = GameEntities.getInstance();
        gameEntities.addEnemyProjectile(new Axe(axeStartPosition, axeVelocity, axeTexture));
    }

    
}