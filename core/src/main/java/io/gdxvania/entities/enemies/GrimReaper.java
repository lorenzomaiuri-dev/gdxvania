package io.gdxvania.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

import io.gdxvania.entities.GameEntities;
import io.gdxvania.entities.player.Player;
import io.gdxvania.utils.Constants;

public class GrimReaper extends Enemy {
	private static final int HEALTH = 3;
	private static final float BASE_SPEED = 0;
    private static final float SPEED_VARIATION = 0; 
    private static final float ITEM_PROBABILITY = 0f;
    private static final int SCORE = 100;
    public static final float SPAWN_RATE = 1f;
    private static final float SICKLE_SPEED = 250;
    private static final float TELEPORT_INTERVAL = 2f;
    private float teleportTimer = 0;
    private Texture sickleTexture;
    private Vector2 targetPosition;
    private float attackInterval = 1f;
    private float attackTimer = 0;

    public GrimReaper(Vector2 startPos) {
        super(startPos, "grim.png", BASE_SPEED, SPEED_VARIATION, ITEM_PROBABILITY, SCORE, SPAWN_RATE);
        super.setHealth(HEALTH);
        super.isBoss = true;
        this.sickleTexture = new Texture("sickle.png");
        this.targetPosition = new Vector2();
    }

    public void setTarget(Vector2 targetPosition) {
        this.targetPosition.set(targetPosition);
    }

    @Override
    public void update(float delta) {
    	updateInvincibility(delta);
        teleportTimer += delta;
        if (teleportTimer >= TELEPORT_INTERVAL) {
            teleport();
            teleportTimer = 0;
        }

        attackTimer += delta;
        if (attackTimer >= attackInterval) {
            attack();
            attackTimer = 0;
        }
    }

    private void teleport() {
        float teleportX = MathUtils.random(targetPosition.x - 50, targetPosition.x + 50);
        float teleportY = MathUtils.random(Constants.GROUND_LEVEL + 15, Constants.SCREEN_HEIGHT - getHeight() - 40);
        position.set(teleportX, teleportY);
    }

    private void attack() {
    	setTarget(Player.getInstance().getPosition());
        Vector2 sickleStartPosition = new Vector2(position.x + getWidth() / 2, position.y + getHeight() / 2);
        Vector2 direction = new Vector2(targetPosition.x - sickleStartPosition.x, targetPosition.y - sickleStartPosition.y).nor();
        Vector2 sickleVelocity = new Vector2(direction).scl(SICKLE_SPEED);
        GameEntities.getInstance().addEnemyProjectile(new Sickle(sickleStartPosition, sickleVelocity, sickleTexture));
    }
}