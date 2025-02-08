package com.la35D2.game.Jugador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class NaveJugador {
    private float x, y;
    private Texture textura;
    private boolean moverIzquierda, moverDerecha;
    private static final float VELOCIDAD = 200f; // Velocidad de movimiento en píxeles por segundo

    private Array<RayoJugador> rayos; // Lista de disparos
    private Texture texturaRayo; // Imagen del disparo

    public NaveJugador(float x, float y, Texture textura) {
        this.x = x;
        this.y = y;
        this.textura = textura;
        this.rayos = new Array<>();
        this.texturaRayo = new Texture("rayo1.png"); // Asegúrate de tener esta imagen
    }

    public void setMoverIzquierda(boolean moverIzquierda) {
        this.moverIzquierda = moverIzquierda;
    }

    public void setMoverDerecha(boolean moverDerecha) {
        this.moverDerecha = moverDerecha;
    }

    public void setDebeDisparar(boolean debeDisparar) {
        if (debeDisparar) {
            rayos.add(new RayoJugador(x + textura.getWidth() / 2, y + textura.getHeight(), texturaRayo));
        }
    }

    public void actualizar(float delta) {
        // Movimiento
        if (moverIzquierda) x -= VELOCIDAD * delta;
        if (moverDerecha) x += VELOCIDAD * delta;

        // Restringir el movimiento dentro de la pantalla
        if (x < 0) x = 0;
        if (x > 800 - textura.getWidth()) x = 800 - textura.getWidth();

        // Actualizar los disparos
        for (RayoJugador rayo : rayos) {
            rayo.actualizar(delta);
        }

        // Eliminar los disparos que salgan de la pantalla
        for (int i = rayos.size - 1; i >= 0; i--) {
            if (rayos.get(i).getY() > 480) {
                rayos.removeIndex(i);
            }
        }
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, x, y);
        for (RayoJugador rayo : rayos) {
            rayo.dibujar(batch);
        }
    }

    public void dispose() {
        textura.dispose();
        texturaRayo.dispose();
        for (RayoJugador rayo : rayos) {
            rayo.dispose();
        }
    }
}
