package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;
    private com.la35D2.game.Player jugadorSeleccionado;  // Agregamos el jugador seleccionado

    // Modificamos el constructor para aceptar un Player
    public GameMapScreen(La35D2 game, com.la35D2.game.Player jugadorSeleccionado) {
        this.game = game;
        this.jugadorSeleccionado = jugadorSeleccionado;  // Asignamos el jugador seleccionado
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        mapTexture = new Texture(Gdx.files.internal("mapa-pixilart.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Dibujar el mapa
        batch.draw(mapTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Dibujar al jugador seleccionado en el mapa
        batch.draw(jugadorSeleccionado.getTexture(), 100, 100, 150, 150); // Puedes ajustar las coordenadas según lo necesites

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
