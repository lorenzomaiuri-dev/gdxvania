package io.gdxvania.gui.screens;

 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.Input;
 import com.badlogic.gdx.Screen;
 import com.badlogic.gdx.graphics.GL20;
 import com.badlogic.gdx.graphics.OrthographicCamera;
 import com.badlogic.gdx.graphics.Texture;
 import com.badlogic.gdx.graphics.g2d.BitmapFont;
 import com.badlogic.gdx.graphics.g2d.SpriteBatch;
 import com.badlogic.gdx.utils.Align;
 import com.badlogic.gdx.utils.viewport.FitViewport;
 import com.badlogic.gdx.utils.viewport.Viewport;
 import io.gdxvania.Main;
import io.gdxvania.SoundManager;
import io.gdxvania.utils.Constants;
import io.gdxvania.utils.ESounds;

 public class MenuScreen implements Screen {
     private static final float VIRTUAL_WIDTH = Constants.SCREEN_WIDTH;
     private static final float VIRTUAL_HEIGHT = Constants.SCREEN_HEIGHT;

     private final Main game;
     private final SpriteBatch batch;
     private final BitmapFont font;
     private Texture characterTexture;
     private float textureWidth;
     private float textureHeight;

     private OrthographicCamera camera;
     private Viewport viewport;

     public MenuScreen(Main game) {
         this.game = game;
         this.batch = game.batch;

         this.font = new BitmapFont();
         this.font.getData().setScale(2f);
         
         characterTexture = new Texture(Gdx.files.internal("castle.png"));
         textureWidth = characterTexture.getWidth();
         textureHeight = characterTexture.getHeight();

         camera = new OrthographicCamera();
         viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
         viewport.apply();
         camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
         camera.update();
     }

     @Override
     public void show() {
    	 SoundManager.getInstance().play(ESounds.TitleScreen, true);
     }
     
     @Override
     public void hide() {
    	 SoundManager.getInstance().stop(ESounds.TitleScreen, true);
     }

     @Override
     public void render(float delta) {
         handleInput();

         Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

         camera.update();
         batch.setProjectionMatrix(camera.combined);

         batch.begin();
         float scaleFactor = 5f;
         float scaledWidth = textureWidth * scaleFactor;
         float scaledHeight = textureHeight * scaleFactor;
         float xPosition = VIRTUAL_WIDTH - scaledWidth + 70;
         float yPosition = 10;

         batch.draw(characterTexture, xPosition, yPosition, scaledWidth, scaledHeight);
         font.draw(batch, "GDXVANIA", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT - 60, 0, Align.center, false);
         font.draw(batch, "A GDXLIB ARCADE CASTLEVANIA!", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT - 130, 0, Align.center, false);
         font.draw(batch, "PLAY (Enter)", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f + 30, 0, Align.center, false);
         font.draw(batch, "EXIT (Escape)", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f - 30, 0, Align.center, false);
         batch.end();
     }

     private void handleInput() {
         if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
             game.setScreen(new TutorialScreen(game));
         }
         if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
             Gdx.app.exit();
         }
     }

     @Override
     public void resize(int width, int height) {
         viewport.update(width, height);
     }

     @Override public void pause() {}
     @Override public void resume() {}

     @Override
     public void dispose() {
         font.dispose();
         characterTexture.dispose();
     }
 }