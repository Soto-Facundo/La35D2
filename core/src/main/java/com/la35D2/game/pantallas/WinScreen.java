package com.la35D2.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.la35D2.game.La35D2;

public class WinScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;

    // Opciones del menú (NO incluye el título "YOU WIN")
    private String[] opciones = {"Volver a jugar", "Cerrar Juego"};
    private int opcionSeleccionada = 0;
    private float tiempo = 0;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    // Puntaje final a mostrar
    private int score;

    public WinScreen(La35D2 game, int score) {
        this.game = game;
        this.score = score;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        batch = new SpriteBatch();
        // Asegúrate de tener este asset, o reemplázalo por otro fondo o dibuja un color de fondo.
        background = new Texture("winPantalla.png");

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Dibujar el fondo
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Dibujar el título (fijo en blanco)
        font.setColor(Color.WHITE);
        GlyphLayout layoutTitulo = new GlyphLayout(font, "YOU WIN");
        font.draw(batch, "YOU WIN", VIRTUAL_WIDTH / 2 - layoutTitulo.width / 2, VIRTUAL_HEIGHT - 50);

        // Dibujar el puntaje
        GlyphLayout layoutScore = new GlyphLayout(font, "Score: " + score);
        font.draw(batch, "Score: " + score, VIRTUAL_WIDTH / 2 - layoutScore.width / 2, VIRTUAL_HEIGHT - 100);

        // Dibujar las opciones de menú centradas
        for (int i = 0; i < opciones.length; i++) {
            if (opcionSeleccionada == i) {
                font.setColor(Color.YELLOW);
            } else {
                font.setColor(Color.WHITE);
            }
            GlyphLayout layoutOpcion = new GlyphLayout(font, opciones[i]);
            float textX = VIRTUAL_WIDTH / 2 - layoutOpcion.width / 2;
            float textY = VIRTUAL_HEIGHT / 2 - 50 - i * 60;
            font.draw(batch, opciones[i], textX, textY);
        }
        batch.end();

        // Control de navegación con teclado
        tiempo += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && tiempo > 0.2f) {
            opcionSeleccionada = (opcionSeleccionada + 1) % opciones.length;
            tiempo = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && tiempo > 0.2f) {
            opcionSeleccionada = (opcionSeleccionada - 1 + opciones.length) % opciones.length;
            tiempo = 0;
        }

        // Acción al presionar Enter
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (opcionSeleccionada == 0) {
                // Volver a jugar: cambia a la pantalla de juego o selección de jugadores
                game.setScreen(new com.la35D2.game.PlayersScreen(game));
            } else if (opcionSeleccionada == 1) {
                Gdx.app.exit();
            }
        }

        // Detección opcional de clics del mouse para seleccionar una opción
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(mousePos);
            for (int i = 0; i < opciones.length; i++) {
                GlyphLayout layoutOpcion = new GlyphLayout(font, opciones[i]);
                float textX = VIRTUAL_WIDTH / 2 - layoutOpcion.width / 2;
                float textY = VIRTUAL_HEIGHT / 2 - 50 - i * 60;
                if (mousePos.x >= textX && mousePos.x <= textX + layoutOpcion.width &&
                    mousePos.y >= textY - font.getLineHeight() && mousePos.y <= textY) {
                    opcionSeleccionada = i;
                    if (i == 0) {
                        game.setScreen(new com.la35D2.game.PlayersScreen(game));
                    } else if (i == 1) {
                        Gdx.app.exit();
                    }
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();
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
        background.dispose();
        font.dispose();
    }
}
