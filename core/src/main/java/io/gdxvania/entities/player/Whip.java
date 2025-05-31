package io.gdxvania.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.gdxvania.utils.Constants;

public class Whip extends Weapon {
    private float attackTimer = 0;
    float offsetY = 15;
    private float playerX, playerY;

    public Whip() {
        texture = new Texture("whip.png");
    }

    @Override
    public void startAttack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = Constants.ATTACK_DURATION;
        }
    }

    @Override
    public void update(float delta) {
    	updateBounds();
        if (isAttacking) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                isAttacking = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
    	updateBounds();
        if (isAttacking) {
            float width = Player.getInstance().getIsFacingRight() ? texture.getWidth() : -texture.getWidth();

            batch.draw(texture,
                       playerX,
                       playerY,
                       width,
                       texture.getHeight());
        }
    }

    @Override
    public Rectangle getBounds() {
    	updateBounds();
    	Player player = Player.getInstance();
        float x = player.getIsFacingRight() ? playerX + texture.getWidth() : playerX - texture.getWidth();
        return new Rectangle(x, playerY, texture.getWidth(), texture.getHeight());
    }

    // get bounds with offset
    private void updateBounds() {
    	Player player = Player.getInstance();
    	Vector2 playerPosition = player.getPosition();
    	float playerWidth = player.getWidth();
        this.playerY = playerPosition.y + offsetY;
        if (player.getIsFacingRight()) {
            this.playerX = playerPosition.x + playerWidth;
        } else {
            this.playerX = playerPosition.x;
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

