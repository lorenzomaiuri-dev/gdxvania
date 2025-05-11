package io.gdxvania.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.gdxvania.GameManager;
import io.gdxvania.entities.enemies.Enemy;
import io.gdxvania.entities.player.Knife;
import io.gdxvania.entities.player.Player;
import io.gdxvania.entities.player.Powerup;
import io.gdxvania.entities.enemies.EnemyProjectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TODO: SPOSTARE DA GAME MANAGER QUI ED EVENUTALI ALTRE CLASSI
public class GameEntities {
	private static GameEntities instance;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Knife> knives = new ArrayList<>();
    private List<Powerup> powerups = new ArrayList<>();
    private List<EnemyProjectile> enemyProjectiles = new ArrayList<>();
    
    private GameEntities() {        
    }
    
    public static GameEntities getInstance() {
        if (instance == null) {
            instance = new GameEntities();
        }
        return instance;
    }
    
    public void reset(){
        instance = null;
    }

    public void updateEntities(float delta, Player player) {
        // Aggiornamento di nemici
        for (Iterator<Enemy> iter = enemies.iterator(); iter.hasNext(); ) {
            Enemy enemy = iter.next();
            enemy.update(delta);
            
            if (enemy.isOffScreen()) {
                iter.remove();
            }
        }

        // Aggiornamento delle Knife
        for (Iterator<Knife> iter = knives.iterator(); iter.hasNext(); ) {
            Knife knife = iter.next();
            knife.update(delta);

            // Controllo collisioni tra knife e nemici
            for (Iterator<Enemy> enemyIter = enemies.iterator(); enemyIter.hasNext(); ) {
                Enemy enemy = enemyIter.next();
                if (knife.getBounds(true).overlaps(enemy.getBounds())) {
                    enemy.takeDamage();
                    if (enemy.isDead()) {
                    	enemyIter.remove();
                    }
                    iter.remove();
                    break;
                }
            }
        }

        // Powerup
        for (Iterator<Powerup> iter = powerups.iterator(); iter.hasNext(); ) {
            Powerup powerup = iter.next();
            if (player.collects(powerup)) {
                player.obtainKnife();
                iter.remove();
            }
        }

        // EnemyProjectile
        for (Iterator<EnemyProjectile> iter = enemyProjectiles.iterator(); iter.hasNext(); ) {
            EnemyProjectile projectile = iter.next();
            projectile.update(delta);
            
            if (projectile.isOffScreen()) {
                iter.remove();
            }

            // Controllo collisioni tra proiettili nemici e il giocatore
            if (player.collidesWith(projectile.getBounds())) {
                player.takeDamage(projectile.getDamage());
                iter.remove();
                if (player.isDead()) {
                	GameManager.getInstance().GameOver();
                }
            }
        }

        // Aggiorna il giocatore (passando la lista dei coltelli)
        player.update(delta, knives);
    }

    public void renderEntities(SpriteBatch batch) {
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
        for (Knife knife : knives) {
            knife.render(batch);
        }
        for (Powerup powerup : powerups) {
            powerup.render(batch);
        }
        for (EnemyProjectile projectile : enemyProjectiles) {
            projectile.render(batch);
        }
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addKnife(Knife knife) {
        knives.add(knife);
    }

    public void addPowerup(Powerup powerup) {
        powerups.add(powerup);
    }

    public void addEnemyProjectile(EnemyProjectile projectile) {
        enemyProjectiles.add(projectile);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Knife> getKnives() {
        return knives;
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }
}