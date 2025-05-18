package io.gdxvania.entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import io.gdxvania.GameManager;
import io.gdxvania.SoundManager;
import io.gdxvania.entities.player.Powerup;
import io.gdxvania.utils.ESounds;

import com.badlogic.gdx.math.MathUtils;

public abstract class Enemy {
	protected int health;
    protected Vector2 position;
    protected Texture texture;
    protected float baseSpeed;
    protected float speedVariation;
    protected float currentSpeed;
    protected float itemProbability;
    protected int score;
    protected float spawnRate;
    protected Vector2 velocity;
    
    protected boolean flipX = false;
    protected boolean flipY = false;
    
    protected boolean isBoss = false;
    
    private boolean isInvincible = false;
    private float invincibleTimer = 0;
    private float invincibleDuration = 1.5f;

    public Enemy(Vector2 startPos, String texturePath, float baseSpeed, float speedVariation, float itemProbability, int score, float spawnRate) {
    	this.health = 1;
        this.position = startPos;
        this.texture = new Texture(texturePath);
        this.baseSpeed = baseSpeed;
        this.speedVariation = speedVariation;
        this.currentSpeed = baseSpeed + MathUtils.random(-speedVariation, speedVariation);
        this.itemProbability = itemProbability;
        this.score = score;
        this.spawnRate = spawnRate;
        this.velocity = new Vector2(0, 0);
    }

    public abstract void update(float delta);
    
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, getWidth(), getHeight());
    }
        
    public void render(SpriteBatch batch) {
        if (isInvincible() && MathUtils.floor(invincibleTimer * 10) % 2 == 0) {
            batch.setColor(1f, 1f, 1f, 0.5f);
            render(batch, texture);
            batch.setColor(1f, 1f, 1f, 1f);
        } else {
            render(batch, texture);
        }
    }
    
    public void render(SpriteBatch batch, Texture spawnedTexture) {
    	TextureRegion region = new TextureRegion(spawnedTexture);
        batch.draw(region,
                   position.x, position.y, // position
                   0, 0, // originX, originY
                   getWidth(), getHeight(), // width, height
                   (flipX ? -1 : 1), (flipY ? -1 : 1), // scaleX, scaleY
                   0); // rotation
    }

    public boolean isOffScreen() {
        return position.x < -getWidth();
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public float getHeight() {
        return texture.getHeight();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    
    public float getBaseSpeed() {
        return this.baseSpeed;
    }
    
    public float getSpeedVariation() {
        return this.speedVariation;
    }
    
    public float getItemProbability() {
        return this.itemProbability;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public float getSpawnRate() {
        return this.spawnRate;
    }

    protected void updateHorizontalMovement(float delta) {
        position.x += velocity.x * delta;
    }
    
    public void setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }
    
    public void takeDamage() {
    	takeDamage(1);
    }
    
    private void takeDamage(int damage) {
    	if (isInvincible()) {
            return;
        }
    	
    	if (damage < 0) {
    		return;
    	}
    	
    	SoundManager.getInstance().play(ESounds.WhipHit, false);
    	
    	this.health -= damage;
    	startInvincibility();
    	
    	if (isDead()) {
    		
    		if (Math.random() < getItemProbability()) {
                GameManager.getInstance().addPowerup(new Powerup(getPosition()));
            }
    		
    		GameManager.getInstance().incrementScore(getScore());
    		
    		if (isBoss) {
    			GameManager.getInstance().Victory();
			}    		
    	}
    }
    
    public boolean isDead() {
    	return this.health <= 0;
    }
    
    protected void setHealth(int amount) {
    	this.health = amount;
    }
    
    public boolean isInvincible() {
        return isInvincible;
    }

    private void startInvincibility() {
        isInvincible = true;
        invincibleTimer = 0;
    }

    protected void updateInvincibility(float delta) {
        if (isInvincible) {
            invincibleTimer += delta;
            if (invincibleTimer >= invincibleDuration) {
                isInvincible = false;
                invincibleTimer = 0;
            }
        }
    }

}