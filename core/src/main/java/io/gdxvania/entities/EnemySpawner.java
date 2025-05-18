package io.gdxvania.entities;

import io.gdxvania.GameManager;
import io.gdxvania.entities.enemies.*;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.gdxvania.utils.Constants;

public class EnemySpawner {
    private float spawnTimer = 0;
    private float minSpawnInterval = 0.8f;
    private float maxSpawnInterval = 2.0f;
    private float currentSpawnInterval = MathUtils.random(minSpawnInterval, maxSpawnInterval);
    private float multipleSpawnProbability = 0.2f;
    private int maxMultipleSpawn = 4;
    private boolean bossSpawned = false;

    public void spawnEnemies(float delta) {
    	
    	if (bossSpawned) {
            return;
        }
    	
    	if (GameManager.getInstance().getScore() > Constants.SCORE_SPAWN_BOSS) {
    		SpawnBoss();
			return;
		}
    	
        spawnTimer += delta;

        if (spawnTimer >= currentSpawnInterval) {
            int numberOfEnemiesToSpawn = 1;
            if (MathUtils.random(0f, 1f) < multipleSpawnProbability) {
                numberOfEnemiesToSpawn = MathUtils.random(2, maxMultipleSpawn);
            }

            for (int i = 0; i < numberOfEnemiesToSpawn; i++) {
                float spawnSide = MathUtils.random(0f, 1f);
                
                float spawnX = 0;
                float spawnY = Constants.GROUND_LEVEL;
                int spawnOffset = (i * 20);
                boolean goingRight = false;

                if (spawnSide <= Constants.ENEMY_SPAWN_RIGHT_RATE) {
                    spawnX = Constants.SCREEN_WIDTH - spawnOffset;                    
                    goingRight = false;
                } else if (spawnSide > Constants.ENEMY_SPAWN_RIGHT_RATE) { 
                    spawnX = spawnOffset;
                    goingRight = true;
                } 
                
                Enemy spawnedEnemy;
                
                if (MathUtils.random(0f, 1f) < AxeMan.SPAWN_RATE) {
                	spawnedEnemy = new AxeMan(new Vector2(spawnX, spawnY));
                }
                else if (MathUtils.random(0f, 1f) < Bat.SPAWN_RATE) {
                	spawnY = MathUtils.random(Constants.GROUND_LEVEL + 20, Constants.GROUND_LEVEL + 50);
                	spawnedEnemy =  new Bat(new Vector2(spawnX, spawnY));
				}
                else {
                	spawnedEnemy = new BasicEnemy(new Vector2(spawnX, spawnY));
                }
                
                float xVelocity = (goingRight) ? spawnedEnemy.getBaseSpeed() : -spawnedEnemy.getBaseSpeed();
                spawnedEnemy.setVelocity(new Vector2(xVelocity, spawnedEnemy.getVelocity().y));
                spawnedEnemy.setFlip(goingRight, false);
                GameEntities.getInstance().addEnemy(spawnedEnemy);
            }

            spawnTimer = 0;
            currentSpawnInterval = MathUtils.random(minSpawnInterval, maxSpawnInterval);
        }
                
    }
    
    public void SpawnBoss() {
    		float bossX = Constants.SCREEN_WIDTH / 2 - 50;
            float bossY = Constants.GROUND_LEVEL;
            Enemy grimReaper = new GrimReaper(new Vector2(bossX, bossY));            
            GameEntities.getInstance().addEnemy(grimReaper);
            bossSpawned = true;
    }
    
}