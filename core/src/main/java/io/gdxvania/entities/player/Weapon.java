package io.gdxvania.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Weapon {
    protected Texture texture;
    protected boolean isAttacking = false;

    public abstract void startAttack();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract Rectangle getBounds();

    public boolean isAttacking() {
        return isAttacking;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
