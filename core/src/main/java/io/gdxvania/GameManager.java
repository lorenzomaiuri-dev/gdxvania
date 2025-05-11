package io.gdxvania;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;
import io.gdxvania.entities.*;
import io.gdxvania.entities.enemies.Enemy;
import io.gdxvania.entities.player.Player;
import io.gdxvania.entities.player.Powerup;
import io.gdxvania.gui.Background;
import io.gdxvania.gui.GuiRenderer;

public class GameManager {
	private static GameManager instance;
    private SpriteBatch batch;
    private Player player;
    private EnemySpawner enemySpawner;
    private Background background;
    private int score = 0;
    private OrthographicCamera cameraGUI;
    private GuiRenderer guiRenderer;
    private boolean isGameOver = false;
    private boolean isVictory = false;

    private GameManager(SpriteBatch batch) {
        this.batch = batch;
        player = Player.getInstance();
        enemySpawner = new EnemySpawner();
        background = new Background("00_bg-level-1.png", 480, 270);
        guiRenderer = new GuiRenderer();
        
        cameraGUI = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cameraGUI.setToOrtho(false);
        cameraGUI.update();
    }
    
    public static GameManager getInstance() {
        return instance;
    }
    
    public static GameManager getInstance(SpriteBatch batch) {
        if (instance == null) {
            instance = new GameManager(batch);
        }
        return instance;
    }
    
    public void reset() {
    	instance = null;
    	GameEntities.getInstance().reset();
    	Player.getInstance().reset();
    }

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        
        GameEntities gameEntities = GameEntities.getInstance();
        
        player.update(delta, gameEntities.getKnives());

        gameEntities.updateEntities(delta, player);

        enemySpawner.spawnEnemies(delta, gameEntities);

        checkPlayerEnemyCollisions();
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        // Background
        batch.setProjectionMatrix(background.getViewport().getCamera().combined);
        background.render(batch);

        // Player
        batch.setProjectionMatrix(background.getViewport().getCamera().combined);
        player.render(batch);
        
        // Entities
        GameEntities gameEntities = GameEntities.getInstance();
        gameEntities.renderEntities(batch);

        // GUI
        batch.setProjectionMatrix(cameraGUI.combined);
        guiRenderer.render(batch, player, score);

        batch.end();
    }

    public void resize(int width, int height) {
        background.getViewport().update(width, height, true);
        cameraGUI.setToOrtho(false, width, height);
        cameraGUI.update();
        background.resize(width, height);
    }

    private void checkPlayerEnemyCollisions() {
    	GameEntities gameEntities = GameEntities.getInstance();
        for (Enemy enemy : gameEntities.getEnemies()) { 
            if (player.collidesWith(enemy)) {
                player.takeDamage();
                if (player.isDead()) {
                    GameOver();
                }
            }
        }
    }
    
    public void GameOver() {
    	isGameOver = true;  
    	reset();
    }
    
    public void Victory() {
    	isVictory = true;
    	reset();
    }

    public void incrementScore(int points) {
        score += points;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean getIsGameOver() {
        return isGameOver;
    }
    
    public boolean getIsVictory() {
        return isVictory;
    }

    public void addPowerup(Powerup powerup) {
    	GameEntities gameEntities = GameEntities.getInstance();
        gameEntities.addPowerup(powerup);
    }

    public void dispose() {
        player.dispose();
        background.dispose();
        guiRenderer.dispose();
    }
}