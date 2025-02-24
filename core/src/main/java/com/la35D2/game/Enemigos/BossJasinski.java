package com.la35D2.game.enemigos;

import com.badlogic.gdx.math.MathUtils; // Para generar números aleatorios
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



public class BossJasinski {
    private float x, y;
    private float velocidadX = 2f; // Velocidad de movimiento horizontal
    private float limiteIzquierdo = 100; // Límite de la pantalla izquierda
    private float limiteDerecho = 924; // Límite de la pantalla derecha
    private float tiempoCambio = 0; // Controla cuándo cambiar de dirección
    private Texture texture;



    public BossJasinski(Texture texture, float x, float y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        // Cambia de dirección aleatoriamente cada cierto tiempo
        tiempoCambio -= delta;
        if (tiempoCambio <= 0) {
            velocidadX = MathUtils.random(-2f, 2f); // Velocidad aleatoria entre -2 y 2
            tiempoCambio = MathUtils.random(1, 3); // Cambia de dirección entre 1 y 3 segundos
        }

        // Mueve el boss
        x += velocidadX;

        // Si toca los bordes, invierte la dirección
        if (x <= limiteIzquierdo || x + texture.getWidth() >= limiteDerecho) {
            velocidadX *= -1;
        }
    }

    public void draw(SpriteBatch batch) {
        float escala = 4.0f; // Cambia este valor para ajustar el tamaño
        batch.draw(texture, x, y, texture.getWidth() * escala, texture.getHeight() * escala);
    }
}
