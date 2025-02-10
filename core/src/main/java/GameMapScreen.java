package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;
    private Player jugadorSeleccionado;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Tamaño original del mapa (ajusta estos valores al tamaño real de tu mapa)
    private static final float MAP_WIDTH = 1024f;  // Ajusta al ancho original de tu mapa
    private static final float MAP_HEIGHT = 768f;  // Ajusta al alto original de tu mapa

    public GameMapScreen(La35D2 game, Player jugadorSeleccionado) {
        this.game = game;
        this.jugadorSeleccionado = jugadorSeleccionado;
    }

    @Override
    public void show() {
        // Crear cámara ortográfica y viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);  // FitViewport para no deformar el mapa
        viewport.apply();

        // Centrar la cámara en el mapa
        camera.position.set(MAP_WIDTH / 2, MAP_HEIGHT / 2, 0);
        camera.update();

        batch = new SpriteBatch();
        mapTexture = new Texture(Gdx.files.internal("mapa-pixilart.png"));

        // Centrar al jugador en el eje X, y posicionarlo en la parte inferior
        float centerX = (Gdx.graphics.getWidth() - jugadorSeleccionado.getTexture().getWidth()) / 2;
        float bottomY = 0;  // Puedes ajustar esto si deseas dejar espacio por debajo

        // Establecer la posición inicial del jugador
        jugadorSeleccionado.setPosition(centerX, bottomY);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizar la posición del jugador
        jugadorSeleccionado.update(delta);

        // Actualizar la cámara (esto es importante para el ajuste en ventana chica y pantalla completa)
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Dibujar el mapa, escalado correctamente para no deformarse
        batch.draw(mapTexture, 0, 0, MAP_WIDTH, MAP_HEIGHT);

        // Dibujar al jugador centrado en la parte inferior de la pantalla
        batch.draw(jugadorSeleccionado.getTexture(), jugadorSeleccionado.getX(), jugadorSeleccionado.getY(), 150, 150); // Ajusta el tamaño si es necesario

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Ajustar el viewport cuando se cambia el tamaño de la ventana
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
    }
}
