package io.gdxvania.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Axe extends EnemyProjectile {
	private static final int DAMAGE = 1;

    public Axe(Vector2 startPos, Vector2 velocity, Texture texture) {
        super(startPos, velocity, texture, DAMAGE);
    }

    @Override
    public void update(float delta) {
        position.x += velocity.x * delta;
        bounds.setPosition(position);
    }
}