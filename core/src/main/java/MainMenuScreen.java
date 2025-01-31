package com.la35D2.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture menuImage;
    private BitmapFont font;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    private float playButtonX, playButtonY, playButtonWidth, playButtonHeight;

    public MainMenuScreen(La35D2 game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        batch = new SpriteBatch();
        menuImage = new Texture("MenuFoto.jpg");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);

        updateButtonPosition();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(menuImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        font.draw(batch, "Play", playButtonX + 100, playButtonY + 60);
        batch.end();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            float mouseX = viewport.unproject(new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
            float mouseY = viewport.unproject(new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;

            if (mouseX >= playButtonX && mouseX <= playButtonX + playButtonWidth &&
                mouseY >= playButtonY && mouseY <= playButtonY + playButtonHeight) {
                System.out.println("¡Play clickeado!");
                game.setScreen(new com.la35D2.game.PlayersScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();
        updateButtonPosition();
    }

    private void updateButtonPosition() {
        playButtonWidth = 400;
        playButtonHeight = 100;
        playButtonX = VIRTUAL_WIDTH / 2 - playButtonWidth / 2 + 75;
        playButtonY = VIRTUAL_HEIGHT / 2 - 155;
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        menuImage.dispose();
        font.dispose();
    }
}
