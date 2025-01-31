package com.la35D2.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;

    public GameMapScreen(La35D2 game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        mapTexture = new Texture("game_map.png");  //
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        mapTexture.dispose();
    }
}
