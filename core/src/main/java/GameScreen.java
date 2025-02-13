package com.la35D2.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.la35D2.game.Jugador.NaveJugador;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private NaveJugador nave;
    private Texture naveTexture; // Variable para la textura de la nave

    @Override
    public void show() {
        batch = new SpriteBatch();
        Globales.batch = batch;

        // Cargar la textura de la nave
        naveTexture = new Texture("nave.png"); // Asegúrate de tener la imagen en la carpeta "assets"

        // Crear la nave pasando la posición y la textura
        nave = new NaveJugador(400, 50, naveTexture); // Pasar la textura al constructor
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpiar la pantalla
        batch.begin();
        nave.update(delta); // Actualizar nave
        nave.draw(); // Dibujar nave
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose(); // Limpiar recursos
        naveTexture.dispose(); // Liberar la textura de la nave
    }

    // Métodos vacíos obligatorios
    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
