package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;

public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;
    private Player jugadorSeleccionado;
    private Texture rayoTexture;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float MAP_WIDTH = 1024f;
    private static final float MAP_HEIGHT = 768f;

    public GameMapScreen(La35D2 game, Player jugadorSeleccionado) {
        this.game = game;
        this.jugadorSeleccionado = jugadorSeleccionado;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);
        viewport.apply();

        camera.position.set(MAP_WIDTH / 2, MAP_HEIGHT / 2, 0);
        camera.update();

        batch = new SpriteBatch();
        Globales.batch = batch; // Inicializamos el SpriteBatch global

        mapTexture = new Texture(Gdx.files.internal("mapa-pixilart.png"));
        rayoTexture = new Texture(Gdx.files.internal("rayo1.png"));

        float centerX = (Gdx.graphics.getWidth() - jugadorSeleccionado.getTexture().getWidth()) / 2;
        float bottomY = 0;

        jugadorSeleccionado.setPosition(centerX, bottomY);

        // Actualizamos la posición de la nave del jugador para que coincida
        jugadorSeleccionado.getNaveJugador().setPosition(centerX, bottomY);
    }

    @Override
    public void render(float delta) {
        // Procesar input
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            jugadorSeleccionado.getNaveJugador().disparar(rayoTexture);
        }

        // Actualizar
        jugadorSeleccionado.update(delta);
        jugadorSeleccionado.getNaveJugador().update(delta);

        // Renderizar
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Dibujar el mapa
        batch.draw(mapTexture, 0, 0, MAP_WIDTH, MAP_HEIGHT);

        // Dibujar al jugador
        batch.draw(jugadorSeleccionado.getTexture(),
            jugadorSeleccionado.getX(),
            jugadorSeleccionado.getY(),
            150, 150);

        // Dibujar la nave y sus rayos
        jugadorSeleccionado.getNaveJugador().draw();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        mapTexture.dispose();
        rayoTexture.dispose();
        Globales.batch = null; // Limpiamos la referencia global
    }
}
