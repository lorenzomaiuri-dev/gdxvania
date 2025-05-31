package io.gdxvania.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.gdxvania.entities.GameEntities;
import io.gdxvania.entities.enemies.Enemy;

import java.util.Iterator;
import java.util.List;

public class AttackManager {
    private final Whip whip;

    public AttackManager() {
        this.whip = new Whip();
    }

    public void update(float delta) {
        whip.update(delta);
        checkCollisionWithEnemies();
    }

    public void startAttack() {
        whip.startAttack();
    }

    public void checkCollisionWithEnemies() {

        if (!whip.isAttacking() || whip.getBounds().getWidth() == 0) return;

        Rectangle whipHitbox = whip.getBounds();

        List<Enemy> enemies = GameEntities.getInstance().getEnemies();
        Iterator<Enemy> iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy enemy = iter.next();
            if (whipHitbox.overlaps(enemy.getBounds())) {
            	enemy.takeDamage();
                if (enemy.isDead()) {
                	iter.remove();
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        whip.render(batch);
    }

    public void dispose() {
        whip.dispose();
    }
}
