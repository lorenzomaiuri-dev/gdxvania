package io.gdxvania.entities.enemies;

import com.badlogic.gdx.math.Vector2;

public class BasicEnemy extends Enemy {
    private static final float BASE_SPEED = 50;
    private static final float SPEED_VARIATION = 0; 
    private static final float ITEM_PROBABILITY = 0.3f;
    private static final int SCORE = 10;
    public static final float SPAWN_RATE = 0.6f;

    public BasicEnemy(Vector2 startPos) {
        super(startPos, "enemy.png", BASE_SPEED, SPEED_VARIATION, ITEM_PROBABILITY, SCORE, SPAWN_RATE);
    }

    @Override
    public void update(float delta) {
        updateHorizontalMovement(delta);
    }
}