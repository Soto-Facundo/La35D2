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

public class GameOverScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;

    // Sólo opciones del menú (no se incluye "GAME OVER")
    private String[] opciones = {"Volver a jugar", "Cerrar Juego"};
    private int opcionSeleccionada = 0;
    private float tiempo = 0;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    public GameOverScreen(La35D2 game) {
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
        // Cambia la ruta de la imagen si es necesario
        background = new Texture("Menu.jpeg");

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // Dibujar el background
        batch.draw(background, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Establecer el color del título a blanco y dibujar "GAME OVER"
        font.setColor(Color.WHITE);
        GlyphLayout layoutGameOver = new GlyphLayout(font, "GAME OVER");
        font.draw(batch, "GAME OVER", VIRTUAL_WIDTH / 2 - layoutGameOver.width / 2, VIRTUAL_HEIGHT - 50);

        // Dibujar las opciones de menú centradas
        for (int i = 0; i < opciones.length; i++) {
            if (opcionSeleccionada == i) {
                font.setColor(Color.YELLOW); // Resalta la opción seleccionada
            } else {
                font.setColor(Color.WHITE);
            }

            GlyphLayout layoutOpcion = new GlyphLayout(font, opciones[i]);
            float textX = VIRTUAL_WIDTH / 2 - layoutOpcion.width / 2;
            float textY = VIRTUAL_HEIGHT / 2 - 50 - i * 60;
            font.draw(batch, opciones[i], textX, textY);
        }
        batch.end();

        // Control de navegación y acciones (el resto del código se mantiene igual)
        tiempo += delta;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && tiempo > 0.2f) {
            opcionSeleccionada = (opcionSeleccionada + 1) % opciones.length;
            tiempo = 0;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && tiempo > 0.2f) {
            opcionSeleccionada = (opcionSeleccionada - 1 + opciones.length) % opciones.length;
            tiempo = 0;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (opcionSeleccionada == 0) {
                game.setScreen(new com.la35D2.game.PlayersScreen(game));
            } else if (opcionSeleccionada == 1) {
                Gdx.app.exit();
            }
        }

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
