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
    private Texture rayoBossTexture;

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

        // 📌 **Aquí agregamos la detección de colisiones**
        Iterator<RayoJugador> iterRayos = jugadorSeleccionado.getNaveJugador().getRayos().iterator();
        while (iterRayos.hasNext()) {
            RayoJugador rayo = iterRayos.next();

            // Verificar colisión con enemigos
            Iterator<com.la35D2.game.enemigos.Enemigo> iterEnemigos = formacionEnemigos.getListaEnemigos().iterator();
            while (iterEnemigos.hasNext()) {
                com.la35D2.game.enemigos.Enemigo enemigo = iterEnemigos.next();

                if (rayo.getBounds().overlaps(enemigo.getBounds())) {
                    System.out.println("¡Colisión detectada! Enemigo eliminado.");
                    iterEnemigos.remove(); // Elimina el enemigo

                    if (formacionEnemigos.getListaEnemigos().isEmpty() && boss == null) {
                        System.out.println("¡Todos los enemigos han sido derrotados! Aparece el Boss.");
                        float bossY = Math.max(0, Math.min(MAP_HEIGHT - 100, MAP_HEIGHT - 370));
                        boss = new BossJasinski(bossTexture, rayoBossTexture, MAP_WIDTH / 2, bossY);
                        System.out.println("Boss Position -> X: " + boss.getX() + ", Y: " + boss.getY());
                    }

                    iterRayos.remove(); // Elimina el rayo
                    break; // Salir del loop de enemigos para evitar errores
                }
            }

            // **Nueva detección de colisión con el boss**
            if (boss != null && rayo.getBounds().overlaps(boss.getBounds())) {
                System.out.println("¡Disparo impactó al Boss!");
                boss.recibirDisparo();
                System.out.println("Vida restante del Boss: " + boss.getVida());
                iterRayos.remove(); // Eliminar el rayo que impactó

                if (boss.getVida() <= 0) {
                    System.out.println("¡Boss eliminado!");
                    boss = null;
                }
                break;
            }
        }



        // Verificar colisión entre el jugador y los enemigos
        for (com.la35D2.game.enemigos.Enemigo enemigo : formacionEnemigos.getListaEnemigos()) {
            if (enemigo.getBounds().overlaps(jugadorSeleccionado.getBounds())) {
                System.out.println("¡El jugador ha sido tocado por un enemigo! GAME OVER");
                game.setScreen(new GameOverScreen(game));
                return; // Detener la ejecución para evitar más procesamiento
            }
        }





        // Verificar colisión entre los rayos del Boss y el jugador
        if (boss != null) {
            Iterator<com.la35D2.game.enemigos.RayoBoss> iterDisparosBoss = boss.getDisparos().iterator();
            while (iterDisparosBoss.hasNext()) {
                com.la35D2.game.enemigos.RayoBoss rayoBoss = iterDisparosBoss.next();

                if (rayoBoss.getBounds().overlaps(jugadorSeleccionado.getBounds())) {
                    System.out.println("¡El jugador ha sido impactado por un rayo del Boss!");
                    impactosRecibidos++;

                    iterDisparosBoss.remove(); // Eliminar el rayo que impactó

                    if (impactosRecibidos >= 3) {
                        System.out.println("¡El jugador ha sido derrotado!");
                        game.setScreen(new GameOverScreen(game));
                    }
                }
            }
        }





        // 🔹 **Dibujar el mapa y los elementos en pantalla**
        batch.begin();
        batch.draw(mapTexture, 0, 0, MAP_WIDTH, MAP_HEIGHT);
        jugadorSeleccionado.getNaveJugador().draw();
        formacionEnemigos.draw(batch);
        batch.end();

        // 🔹 **Dibujar el boss solo si está en la pantalla**
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
