package io.gdxvania.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import io.gdxvania.utils.Constants;

public class Knife extends Weapon {
    private float x, y;
    private boolean isFacingRight = Player.getInstance().getIsFacingRight();

    public Knife(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("knife.png");
    }

    @Override
    public void startAttack() {
    }

    @Override
    public void update(float delta) {
    	float knifeSpeed = isFacingRight ? Constants.KNIFE_SPEED_MULTIPLIER : -Constants.KNIFE_SPEED_MULTIPLIER ;
        x += knifeSpeed * delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion region = new TextureRegion(texture);
        batch.draw(region,
                   x, y, // position
                   0, 0, // originX, originY
                   texture.getWidth(), texture.getHeight(), // width, height
                   (isFacingRight ? 1 : -1), 1, // scaleX, scaleY
                   0); // rotation
    }

    @Override
    public Rectangle getBounds(boolean isFacingRight) {
    	return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    
    @Override
    public void dispose() {
        texture.dispose();
    }
}

