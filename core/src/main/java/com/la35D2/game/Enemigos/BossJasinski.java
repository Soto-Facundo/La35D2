package com.la35D2.game.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BossJasinski {
    private float x, y;
    private Texture texture;

    // Cambiar el orden de los parámetros: primero la textura y luego las posiciones
    public BossJasinski(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        // Aquí se puede agregar movimiento o lógica especial para el boss
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
