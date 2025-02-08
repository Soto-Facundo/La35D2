package com.la35D2.game;

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

public class MainMenuScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture menuImage;
    private BitmapFont font;

    private String[] opciones = {"Play", "Online", "Exit"};
    private int opc = 0;
    private float tiempo = 0;

    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    private OrthographicCamera camera;
    private Viewport viewport;

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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibuja la imagen de fondo correctamente
        batch.draw(menuImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        // Dibuja las opciones de menú centradas
        for (int i = 0; i < opciones.length; i++) {
            if (opc == i) {
                font.setColor(Color.YELLOW); // Resalta la opción seleccionada
            } else {
                font.setColor(Color.WHITE);
            }

            GlyphLayout layout = new GlyphLayout(font, opciones[i]);
            float textWidth = layout.width;
            float ajusteExtra = opciones[i].equals("Online") ? 10 : 0; // Ajusta según sea necesario
            float textX = VIRTUAL_WIDTH / 2 - textWidth / 2 + 1 + ajusteExtra;
            float textY = VIRTUAL_HEIGHT / 2 - 80 - 60 * i;

            font.draw(batch, opciones[i], textX, textY);
        }

        batch.end();

        // Control de navegación
        tiempo += delta;

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (tiempo > 0.1f) {
                tiempo = 0;
                opc = (opc + 1) % opciones.length;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (tiempo > 0.1f) {
                tiempo = 0;
                opc = (opc - 1 + opciones.length) % opciones.length;
            }
        }

        // Detección de clic con el mouse
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(mousePos);

            for (int i = 0; i < opciones.length; i++) {
                GlyphLayout layout = new GlyphLayout(font, opciones[i]);
                float textWidth = layout.width;
                float ajusteExtra = opciones[i].equals("Online") ? 10 : 0;
                float textX = VIRTUAL_WIDTH / 2 - textWidth / 2 + 1 + ajusteExtra;
                float textY = VIRTUAL_HEIGHT / 2 - 80 - 60 * i;

                if (mousePos.x >= textX && mousePos.x <= textX + textWidth &&
                    mousePos.y >= textY - font.getLineHeight() && mousePos.y <= textY) {
                    opc = i;
                    break;
                }
            }
        }

        // Acción al presionar Enter
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (opc == 0) {
                game.setScreen(new com.la35D2.game.PlayersScreen(game));
            } else if (opc == 1) {
                // Aquí podrías agregar la lógica para el modo online
            } else if (opc == 2) {
                Gdx.app.exit();
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
        menuImage.dispose();
        font.dispose();
    }
}
