package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.SortedIntList;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;
import com.la35D2.game.Jugador.RayoJugador;
import com.la35D2.game.enemigos.FormacionEnemigos;
import com.la35D2.game.Jugador.NaveJugador;
import com.la35D2.game.pantallas.GameOverScreen;
import java.util.Iterator;
import javax.swing.text.html.HTMLDocument;


public class GameMapScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture mapTexture;
    private com.la35D2.game.Player jugadorSeleccionado;
    private Texture rayoTexture;

    private OrthographicCamera camera;
    private Viewport viewport;

    // Eliminamos la lista de enemigos fijos
    // private ArrayList<com.la35D2.game.enemigos.Enemigo> enemigos;

    // Nueva variable para la formación de enemigos con movimiento
    private FormacionEnemigos formacionEnemigos;

    private static final float MAP_WIDTH = 1024f;
    private static final float MAP_HEIGHT = 768f;

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
        Globales.batch = batch; // Inicializamos el SpriteBatch global

        mapTexture = new Texture(Gdx.files.internal("mapa-pixilart.png"));
        rayoTexture = new Texture(Gdx.files.internal("rayo1.png"));

        // Posicionar la nave del jugador en el centro inferior del mapa
        float centerX = (Gdx.graphics.getWidth() - jugadorSeleccionado.getTexture().getWidth()) / 2;
        float bottomY = 0;
        jugadorSeleccionado.setPosition(centerX, bottomY);
        jugadorSeleccionado.getNaveJugador().setPosition(centerX, bottomY);

        //la formación de enemigos que se moverán, admeas aqui se puede agregar enemigos
        Texture enemigoTexture = new Texture(Gdx.files.internal("enemigo.png"));
        formacionEnemigos = new FormacionEnemigos(enemigoTexture, 3, 7, 100);
    }

    @Override
    public void render(float delta) {
        // Procesar input para disparar y mover la nave
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            jugadorSeleccionado.getNaveJugador().disparar(rayoTexture);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            jugadorSeleccionado.getNaveJugador().setPosition(jugadorSeleccionado.getX() - 5, jugadorSeleccionado.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            jugadorSeleccionado.getNaveJugador().setPosition(jugadorSeleccionado.getX() + 5, jugadorSeleccionado.getY());
        }

        // Actualizar la nave y sus rayos
        jugadorSeleccionado.update(delta);
        jugadorSeleccionado.getNaveJugador().update(delta);

        // Actualizar la formación de enemigos con la lógica de movimiento
        formacionEnemigos.update(delta);

        // Renderizar
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // Dibujar el mapa
        batch.draw(mapTexture, 0, 0, MAP_WIDTH, MAP_HEIGHT);
        // Dibujar la nave del jugador
        jugadorSeleccionado.getNaveJugador().draw();
        // Dibujar la formación de enemigos en movimiento
        formacionEnemigos.draw(batch);
        batch.end();

        // Actualizar la formación de enemigos con la lógica de movimiento
        formacionEnemigos.update(delta);

// Comprobar colisiones entre la nave del jugador y cada enemigo
        for (com.la35D2.game.enemigos.Enemigo enemigo : formacionEnemigos.getEnemigos()) {
            if (jugadorSeleccionado.getNaveJugador().getBounds().overlaps(enemigo.getBounds())) {
                System.out.println("¡Colisión detectada! GAME OVER");
                game.setScreen(new GameOverScreen(game));
                break;  // Opcional: detener el ciclo tras detectar la colisión
            }
        }

        Iterator<RayoJugador> iterRayos = jugadorSeleccionado.getNaveJugador().getRayos().iterator();
        while (iterRayos.hasNext()) {
            RayoJugador rayo = iterRayos.next();

            Iterator<com.la35D2.game.enemigos.Enemigo> iterEnemigos = formacionEnemigos.getListaEnemigos().iterator();
            while (iterEnemigos.hasNext()) {
                com.la35D2.game.enemigos.Enemigo enemigo = iterEnemigos.next();

                if (rayo.getBounds().overlaps(enemigo.getBounds())) {
                    iterEnemigos.remove(); // Eliminar enemigo
                    iterRayos.remove(); // Eliminar rayo
                    break; // Salir del loop porque ya eliminamos el enemigo
                }
            }
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
        // Si la clase FormacionEnemigos tuviera recursos a liberar, se haría aquí
    }
}


