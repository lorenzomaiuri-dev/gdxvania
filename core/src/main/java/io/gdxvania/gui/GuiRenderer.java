package io.gdxvania.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import io.gdxvania.entities.player.Player;

public class GuiRenderer {
    private BitmapFont font;    
    private final Texture healthBar = new Texture("healthbar.png");
    private final Texture knifeIconTexture = new Texture("knife.png");

    public GuiRenderer() {
        font = new BitmapFont();
        font.getData().setScale(2);
    }

    public void render(SpriteBatch batch, int score) {
    	Player player = Player.getInstance();
        drawHealthBar(batch, player.getCurrentHealth());
        drawKnivesCount(batch, player.getCurrentKnives());
        drawCurrentScore(batch, score);
    }

    public void dispose() {
        font.dispose();
        healthBar.dispose();
    }
    
    public void drawHealthBar(SpriteBatch batch, int health) {
        for (int i = 0; i < health; i++) {
            batch.draw(healthBar, 10 + (i * 20), Gdx.graphics.getHeight() - 30);
        }
    }
    
    public void drawKnivesCount(SpriteBatch batch, int knivesCount) {
        batch.draw(knifeIconTexture, 10, Gdx.graphics.getHeight() - 110, 32, 32);
        float textY = Gdx.graphics.getHeight() - 100 + font.getCapHeight();
        font.draw(batch, "x " + knivesCount, 10 + 32, textY);
    }
    
    public void drawCurrentScore(SpriteBatch batch, int score) {
    	font.draw(batch, "Score: " + score, 10, Gdx.graphics.getHeight() - 50);
    }
}