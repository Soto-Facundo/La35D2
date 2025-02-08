package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.la35D2.game.Jugador.NaveJugador;

public class GameScreen implements Screen {
    private NaveJugador naveJugador;
    private SpriteBatch batch;

    public GameScreen(com.la35D2.game.Player jugadorSeleccionado) {
        // Usa la imagen del jugador seleccionado
        naveJugador = new NaveJugador(400, 50, jugadorSeleccionado.getTexture());
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            naveJugador.setMoverIzquierda(true);
        } else {
            naveJugador.setMoverIzquierda(false);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            naveJugador.setMoverDerecha(true);
        } else {
            naveJugador.setMoverDerecha(false);
        }

        naveJugador.actualizar(delta); // 🔴 Asegúrate de que esta línea esté presente

        batch.begin();
        naveJugador.dibujar(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        naveJugador.dispose();
        batch.dispose();
    }

    // Métodos vacíos (necesarios para implementar Screen)
    @Override public void resize(int width, int height) {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
