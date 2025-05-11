package io.gdxvania.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.gdxvania.utils.Constants;
import io.gdxvania.entities.GameEntities;
import io.gdxvania.entities.enemies.Enemy;

import java.util.List;

public class Player {
	private static Player instance;
    private Vector2 position = new Vector2(100, Constants.GROUND_LEVEL);
    private float velocityY = 0;
    private boolean isFacingRight = true;
    private boolean isJumping = false;
    private boolean knifeKeyPressed = false;
    private int knifeCount = 0;
    private int health = Constants.PLAYER_HEALTH;
    private float blinkTimer = 0;
    private boolean isVisible = true;
    private float damageTimer = 0;
    private final Whip whip = new Whip();
    private AttackManager attackManager;

    private final Texture texture = new Texture("player.png");        
    
    private Player() {
        attackManager = new AttackManager(this, whip);                       
    }
    
    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }
    
    public void reset(){
        instance = null;
    }

    public void update(float delta, List<Knife> knives) {
        handleInput(delta, knives);
        GameEntities gameEntities = GameEntities.getInstance();
        attackManager.update(delta, this, gameEntities.getEnemies());
        applyPhysics(delta);

        if (damageTimer > 0) {
            damageTimer -= delta;
            blinkTimer += delta;
            if (blinkTimer >= Constants.BLINK_INTERVAL) {
                isVisible = !isVisible;
                blinkTimer -= Constants.BLINK_INTERVAL;
            }
        } else {
            isVisible = true;
        }
    }

    private void handleInput(float delta, List<Knife> knives) {
    	boolean movingLeft = Gdx.input.isKeyPressed(Constants.LEFT_DIRECTION_KEY);
        boolean movingRight = Gdx.input.isKeyPressed(Constants.RIGHT_DIRECTION_KEY);

        handleMovement(delta, movingLeft, movingRight);


        if (Gdx.input.isKeyJustPressed(Constants.JUMP_KEY)) {
        	handleJump();            
        }

        if (Gdx.input.isKeyJustPressed(Constants.WHIP_KEY)) {        	
            attackManager.startAttack();
        }
        
        handleKnife(knives);
    }

	private void handleJump() {
		if (!isJumping) {
			velocityY = Constants.JUMP_FORCE;
		    isJumping = true;	
		}
	}

	private void handleKnife(List<Knife> knives) {
		boolean isKnifeKeyDown = Gdx.input.isKeyPressed(Constants.KNIFE_KEY);
        if (isKnifeKeyDown && !knifeKeyPressed && knifeCount > 0) {
            float knifeOffsetX = isFacingRight ? texture.getWidth() : 0;
            knives.add(new Knife(position.x + knifeOffsetX, position.y + 10));
            knifeCount--;
            knifeKeyPressed = true;
        }
        if (!isKnifeKeyDown) {
            knifeKeyPressed = false;
        }
	}

	private void handleMovement(float delta, boolean movingLeft, boolean movingRight) {
		if (movingLeft) {
            position.x -= Constants.PLAYER_SPEED * delta;
            isFacingRight = false;
        }
        if (movingRight) {
            position.x += Constants.PLAYER_SPEED * delta;
            isFacingRight = true;
        }
        
        // prevent leaving screen
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > Constants.SCREEN_WIDTH - texture.getWidth()) {
            position.x = Constants.SCREEN_WIDTH - texture.getWidth();
        }
	}

    private void applyPhysics(float delta) {
        velocityY += Constants.GRAVITY * delta;
        position.y += velocityY * delta;

        if (position.y <= Constants.GROUND_LEVEL) {
            position.y = Constants.GROUND_LEVEL;
            velocityY = 0;
            isJumping = false;
        }
    }

    public void render(SpriteBatch batch) {
        if (isVisible) {
            batch.draw(texture,
                       !isFacingRight ? position.x : position.x + texture.getWidth(),
                       position.y,
                       !isFacingRight ? texture.getWidth() : -texture.getWidth(),
                       texture.getHeight());
        }
        attackManager.render(batch, this);
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }
    
    public boolean collidesWith(Rectangle bounds) {
        return getBounds().overlaps(bounds) && damageTimer <= 0;
    }

    public boolean collidesWith(Enemy enemy) {
        return collidesWith(enemy.getBounds());
    }
 
    public void takeDamage() {
        takeDamage(1);
    }
    
    public void takeDamage(int damage) {
        if (damageTimer <= 0) {
            health -= damage;
            damageTimer = Constants.DAMAGE_COOLDOWN;
            blinkTimer = 0; 
            isVisible = false;
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean collects(Powerup p) {
        return getBounds().overlaps(p.getBounds());
    }

    public void obtainKnife() {
        knifeCount++;
    }

    public void dispose() {
        texture.dispose();
        whip.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public float getHeight() {
        return texture.getHeight();
    }
    
    public int getCurrentHealth() {
        return this.health;
    }
    
    public int getCurrentKnives() {
        return this.knifeCount;
    }
    
    public boolean getIsFacingRight() {
        return this.isFacingRight;
    }
}
