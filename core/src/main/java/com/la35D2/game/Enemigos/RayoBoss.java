package com.la35D2.game.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class RayoBoss {
    private float x, y;
    private float velocidadY = -5; // Velocidad hacia abajo
    private Texture texture;
    private boolean eliminado = false; // Para saber si el rayo sigue activo

    public RayoBoss(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        y += velocidadY;

        // Si el disparo sale de la pantalla, se marca como eliminado
        if (y < 0) {
            eliminado = true;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public boolean isEliminado() {
        return eliminado;
    }
}
