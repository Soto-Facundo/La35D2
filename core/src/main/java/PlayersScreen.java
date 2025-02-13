package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.la35D2.game.Jugador.NaveJugador;

public class PlayersScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private BitmapFont font;
    private com.la35D2.game.Player quispe;
    private com.la35D2.game.Player soto;
    private OrthographicCamera camera;
    private Viewport viewport;

    private String[] opciones = {"Quispe", "Soto"};
    private int opc = 0;
    private float tiempo = 0;

    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    private com.la35D2.game.Player playerSeleccionado;

    public PlayersScreen(La35D2 game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();
        backgroundImage = new Texture("PlayersBackground.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);

        // Crear la textura para la nave de Quispe
        Texture texturaQuispe = new Texture("QuispePixel.png");
        NaveJugador naveQuispe = new NaveJugador(VIRTUAL_WIDTH / 2 - 200, 200, texturaQuispe);

        // Crear el jugador Quispe
        quispe = new com.la35D2.game.Player("Quispe", "QuispePixel.png", VIRTUAL_WIDTH / 2 - 200, 200, naveQuispe);

        // Crear la textura para la nave de Soto
        Texture texturaSoto = new Texture("SotoPixel.png");
        NaveJugador naveSoto = new NaveJugador(VIRTUAL_WIDTH / 2 + 200, 200, texturaSoto);

        // Crear el jugador Soto
        soto = new com.la35D2.game.Player("Soto", "SotoPixel.png", VIRTUAL_WIDTH / 2 + 200, 200, naveSoto);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Dibujar el texto "Seleccione a su jugador"
        font.setColor(Color.WHITE);
        font.draw(batch, "Seleccione a su jugador", VIRTUAL_WIDTH / 2 - 150, 400);

        // Dibujar las imágenes de los jugadores
        batch.draw(quispe.getTexture(), quispe.getX(), quispe.getY(), 150, 150);
        batch.draw(soto.getTexture(), soto.getX(), soto.getY(), 150, 150);

        // Resaltar la opción seleccionada
        if (opc == 0) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, opciones[0], quispe.getX() + 25, 150);

        if (opc == 1) {
            font.setColor(Color.YELLOW);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, opciones[1], soto.getX() + 25, 150);
        // Dibujar la nave y los rayos del jugador seleccionado

        if (playerSeleccionado != null) {
            playerSeleccionado.getNaveJugador().draw();
        }


        batch.end();

        tiempo += delta;

        // Control de navegación con las flechas
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (tiempo > 0.1f) {
                tiempo = 0;
                opc = (opc + 1) % opciones.length;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (tiempo > 0.1f) {
                tiempo = 0;
                opc = (opc - 1 + opciones.length) % opciones.length;
            }
        }

        // Acción al presionar Enter
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (opc == 0) {
                playerSeleccionado = quispe;
            } else if (opc == 1) {
                playerSeleccionado = soto;
            }
            System.out.println("Jugador seleccionado: " + playerSeleccionado.getName());
            game.setScreen(new com.la35D2.game.GameMapScreen(game, playerSeleccionado));
        }

        // Disparar con la barra espaciadora
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (playerSeleccionado != null) {
                playerSeleccionado.getNaveJugador().disparar(new Texture("rayo1.png")); // Pasa la textura del rayo
            }
        }

        // Actualizar la nave y rayos
        if (playerSeleccionado != null) {
            playerSeleccionado.getNaveJugador().update(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
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
        backgroundImage.dispose();
        font.dispose();
        quispe.dispose();
        soto.dispose();
    }
}
