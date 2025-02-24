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
import com.la35D2.game.pantallas.WinScreen;
import java.util.Iterator;

public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;
    private com.la35D2.game.Player jugadorSeleccionado;
    private Texture rayoTexture;
    private Texture rayoBossTexture;

    // Variables para puntaje y tiempo
    private float tiempoTranscurrido = 0;
    private int unusedShots = 0;

    private OrthographicCamera camera;
    private Viewport viewport;

    private FormacionEnemigos formacionEnemigos;
    private static final float MAP_WIDTH = 1024f;
    private static final float MAP_HEIGHT = 768f;

    private BossJasinski boss;
    private Texture bossTexture;

    private int impactosRecibidos = 0;

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
        rayoBossTexture = new Texture(Gdx.files.internal("rayoBoss.png"));
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
        // Actualizar entradas y el jugador
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

        // Incrementar tiempo transcurrido
        tiempoTranscurrido += delta;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // --- Detección de colisiones entre rayos del jugador y enemigos / boss ---
        Iterator<RayoJugador> iterRayos = jugadorSeleccionado.getNaveJugador().getRayos().iterator();
        while (iterRayos.hasNext()) {
            RayoJugador rayo = iterRayos.next();

            // Colisión con enemigos
            Iterator<com.la35D2.game.enemigos.Enemigo> iterEnemigos = formacionEnemigos.getListaEnemigos().iterator();
            while (iterEnemigos.hasNext()) {
                com.la35D2.game.enemigos.Enemigo enemigo = iterEnemigos.next();
                if (rayo.getBounds().overlaps(enemigo.getBounds())) {
                    System.out.println("¡Colisión detectada! Enemigo eliminado.");
                    iterEnemigos.remove();
                    iterRayos.remove();
                    break;
                }
            }

            // Colisión con el Boss
            if (boss != null && rayo.getBounds().overlaps(boss.getBounds())) {
                System.out.println("¡Disparo impactó al Boss!");
                boss.recibirDisparo();
                System.out.println("Vida restante del Boss: " + boss.getVida());
                iterRayos.remove();
                if (boss.getVida() <= 0) {
                    System.out.println("¡Boss derrotado!");
                    // Calculamos puntaje y cambiamos a la pantalla WinScreen
                    int score = calcularScore();
                    game.setScreen(new WinScreen(game, score));
                    return;
                }
                break;
            }
        }
        // --- Fin de detección de colisiones de rayos ---

        // --- Si ya no hay enemigos y el Boss aún no ha aparecido, crearlo ---
        if (formacionEnemigos.getListaEnemigos().isEmpty() && boss == null) {
            System.out.println("¡Todos los enemigos han sido derrotados! Aparece el Boss.");
            float bossY = Math.max(0, Math.min(MAP_HEIGHT - 100, MAP_HEIGHT - 350));
            boss = new BossJasinski(bossTexture, rayoBossTexture, MAP_WIDTH / 2, bossY);
            System.out.println("Boss Position -> X: " + boss.getX() + ", Y: " + boss.getY());
        }
        // --- Fin de creación del Boss ---

        // --- Verificar colisión entre el jugador y los enemigos ---
        for (com.la35D2.game.enemigos.Enemigo enemigo : formacionEnemigos.getListaEnemigos()) {
            if (enemigo.getBounds().overlaps(jugadorSeleccionado.getBounds())) {
                System.out.println("¡El jugador ha sido tocado por un enemigo! GAME OVER");
                game.setScreen(new GameOverScreen(game));
                return;
            }
        }
        // --- Fin de verificación de colisión con enemigos ---

        // --- Verificar colisión entre los rayos del Boss y el jugador ---
        if (boss != null) {
            Iterator<com.la35D2.game.enemigos.RayoBoss> iterDisparosBoss = boss.getDisparos().iterator();
            while (iterDisparosBoss.hasNext()) {
                com.la35D2.game.enemigos.RayoBoss rayoBoss = iterDisparosBoss.next();
                if (rayoBoss.getBounds().overlaps(jugadorSeleccionado.getBounds())) {
                    System.out.println("¡El jugador ha sido impactado por un rayo del Boss!");
                    impactosRecibidos++;
                    iterDisparosBoss.remove();
                    if (impactosRecibidos >= 3) {
                        System.out.println("¡El jugador ha sido derrotado!");
                        game.setScreen(new GameOverScreen(game));
                        return;
                    }
                }
            }
        }
        // --- Fin de colisión de rayos del Boss con el jugador ---

        // --- Dibujar elementos en pantalla ---
        batch.begin();
        batch.draw(mapTexture, 0, 0, MAP_WIDTH, MAP_HEIGHT);
        jugadorSeleccionado.getNaveJugador().draw();
        formacionEnemigos.draw(batch);
        batch.end();

        // --- Dibujar y actualizar al Boss (si existe) ---
        if (boss != null) {
            boss.update(delta);
            batch.begin();
            boss.draw(batch);
            batch.end();
        }
    }

    private int calcularScore() {
        return Math.max(0, 1000 - (int)(tiempoTranscurrido * 10)) + (unusedShots * 20);
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
