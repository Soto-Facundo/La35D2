package com.la35D2.game.Jugador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RayoJugador {
    private float x, y;
    private Texture textura;
    private static final float VELOCIDAD = 300f;

    public RayoJugador(float x, float y, Texture textura) {
        this.x = x;
        this.y = y;
        this.textura = textura;
    }

    public void actualizar(float delta) {
        y += VELOCIDAD * delta;
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, x, y);
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        textura.dispose();
    }
}
