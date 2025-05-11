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
 import io.gdxvania.utils.Constants;

 public class VictoryScreen implements Screen {
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

     public VictoryScreen(Main game) {
         this.game = game;
         this.batch = game.batch;

         this.font = new BitmapFont();
         this.font.getData().setScale(2f);
         
         characterTexture = new Texture(Gdx.files.internal("dracula.png"));
         textureWidth = characterTexture.getWidth();
         textureHeight = characterTexture.getHeight();

         camera = new OrthographicCamera();
         viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
         viewport.apply();
         camera.position.set(VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f, 0);
         camera.update();
     }

     @Override
     public void show() {}

     @Override
     public void render(float delta) {
         handleInput();

         Gdx.gl.glClearColor(0.1f, 0f, 0f, 1f);
         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

         camera.update();
         batch.setProjectionMatrix(camera.combined);

         batch.begin();
         float scaleFactor = 1.5f;
         float scaledWidth = textureWidth * scaleFactor;
         float scaledHeight = textureHeight * scaleFactor;
         float xPosition = VIRTUAL_WIDTH - scaledWidth - 20;
         float yPosition = 20;

         batch.draw(characterTexture, xPosition, yPosition, scaledWidth, scaledHeight);
         font.draw(batch, "THANKS SIMON!", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT - 60, 0, Align.center, false);
         font.draw(batch, "BUT DRACULA", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT - 100, 0, Align.center, false);
         font.draw(batch, "IS IN ANOTHER CASTLE!", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT - 130, 0, Align.center, false);
         font.draw(batch, "RETURN TO MENU (Enter)", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f + 30, 0, Align.center, false);
         font.draw(batch, "END (Escape)", VIRTUAL_WIDTH / 2f, VIRTUAL_HEIGHT / 2f - 30, 0, Align.center, false);
         batch.end();
     }

     private void handleInput() {
         if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
             game.setScreen(new MenuScreen(game));
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
     @Override public void hide() {}

     @Override
     public void dispose() {
         font.dispose();
         characterTexture.dispose();
     }
 }