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
import com.la35D2.game.Jugador.RayoJugador;
import com.la35D2.game.enemigos.FormacionEnemigos;
import com.la35D2.game.Jugador.NaveJugador;
import com.la35D2.game.pantallas.GameOverScreen;
import com.la35D2.game.enemigos.BossJasinski;
import java.util.Iterator;

public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;
    private com.la35D2.game.Player jugadorSeleccionado;
    private Texture rayoTexture;

    private OrthographicCamera camera;
    private Viewport viewport;

    private FormacionEnemigos formacionEnemigos;
    private static final float MAP_WIDTH = 1024f;
    private static final float MAP_HEIGHT = 768f;

    private BossJasinski boss;
    private Texture bossTexture;

    public GameMapScreen(La35D2 game, com.la35D2.game.Player jugadorSeleccionado) {
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
        Globales.batch = batch;

        mapTexture = new Texture(Gdx.files.internal("mapa-pixilart.png"));
        rayoTexture = new Texture(Gdx.files.internal("rayo1.png"));
        bossTexture = new Texture(Gdx.files.internal("jBossFase.png"));

        float centerX = (Gdx.graphics.getWidth() - jugadorSeleccionado.getTexture().getWidth()) / 2;
        float bottomY = 0;
        jugadorSeleccionado.setPosition(centerX, bottomY);
        jugadorSeleccionado.getNaveJugador().setPosition(centerX, bottomY);

        Texture enemigoTexture = new Texture(Gdx.files.internal("enemigo.png"));
        formacionEnemigos = new FormacionEnemigos(enemigoTexture, 3, 7, 100);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            jugadorSeleccionado.getNaveJugador().disparar(rayoTexture);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            jugadorSeleccionado.getNaveJugador().setPosition(jugadorSeleccionado.getX() - 5, jugadorSeleccionado.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            jugadorSeleccionado.getNaveJugador().setPosition(jugadorSeleccionado.getX() + 5, jugadorSeleccionado.getY());
        }

        jugadorSeleccionado.update(delta);
        jugadorSeleccionado.getNaveJugador().update(delta);

        if (!formacionEnemigos.getListaEnemigos().isEmpty()) {
            formacionEnemigos.update(delta);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(mapTexture, 0, 0, MAP_WIDTH, MAP_HEIGHT);
        jugadorSeleccionado.getNaveJugador().draw();
        formacionEnemigos.draw(batch);
        batch.end();

        for (com.la35D2.game.enemigos.Enemigo enemigo : formacionEnemigos.getListaEnemigos()) {
            if (jugadorSeleccionado.getNaveJugador().getBounds().overlaps(enemigo.getBounds())) {
                System.out.println("¡Colisión detectada! GAME OVER");
                game.setScreen(new GameOverScreen(game));
                break;
            }
        }

        Iterator<RayoJugador> iterRayos = jugadorSeleccionado.getNaveJugador().getRayos().iterator();
        while (iterRayos.hasNext()) {
            RayoJugador rayo = iterRayos.next();
            Iterator<com.la35D2.game.enemigos.Enemigo> iterEnemigos = formacionEnemigos.getListaEnemigos().iterator();
            while (iterEnemigos.hasNext()) {
                com.la35D2.game.enemigos.Enemigo enemigo = iterEnemigos.next();
                if (rayo.getBounds().overlaps(enemigo.getBounds())) {
                    iterEnemigos.remove();
                    iterRayos.remove();
                    break;
                }
            }
        }

        if (formacionEnemigos.getListaEnemigos().isEmpty() && boss == null) {
            System.out.println("¡Todos los enemigos eliminados! Aparece el BossJasinski");
            boss = new BossJasinski(bossTexture, MAP_WIDTH / 2 - 200, MAP_HEIGHT - 350);
        }

        if (boss != null) {
            boss.update(delta);
            batch.begin();
            boss.draw(batch);
            batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(MAP_WIDTH / 2, MAP_HEIGHT / 2, 0);
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
        mapTexture.dispose();
        rayoTexture.dispose();
        bossTexture.dispose();
    }
}
