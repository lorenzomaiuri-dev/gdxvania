package io.gdxvania.entities.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.gdxvania.entities.enemies.Enemy;

import java.util.Iterator;
import java.util.List;

public class AttackManager {
    private Whip whip;
    private Player player;

    public AttackManager(Player player, Whip whip) {
        this.whip = whip;
        this.player = player;
    }

    public void update(float delta, Player player, List<Enemy> enemies) {
        whip.update(delta);
        this.player = player;
        whip.updateBounds(player.getPosition(), player.getWidth(), player.getPosition().y, player.getIsFacingRight());
        checkCollisionWithEnemies(enemies);
    }

    public void startAttack() {
        whip.startAttack();
    }
    
    public void checkCollisionWithEnemies(List<Enemy> enemies) {    	
    	whip.updateBounds(player.getPosition(), player.getWidth(), player.getPosition().y, player.getIsFacingRight());
    	
        if (!whip.isAttacking() || whip.getBounds(player.getIsFacingRight()).getWidth() == 0) return;
        
        Rectangle whipHitbox = whip.getBounds(player.getIsFacingRight());

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

    public void render(SpriteBatch batch, Player player) {
    	whip.updateBounds(player.getPosition(), player.getWidth(), player.getPosition().y, player.getIsFacingRight());
        whip.render(batch);
    }
    
    public void dispose() {
        whip.dispose();
    }
}
