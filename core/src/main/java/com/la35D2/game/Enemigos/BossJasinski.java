package com.la35D2.game.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BossJasinski {
    private float x, y;
    private float velocidadX = 2f;
    private float limiteIzquierdo = 100;
    private float limiteDerecho = 924;
    private float tiempoCambio = 0;
    private Texture texture;
    private int vida = 35;
    private Texture rayoTexture;
    private List<com.la35D2.game.enemigos.RayoBoss> disparos;
    private float tiempoDisparo = 0;

    public BossJasinski(Texture texture, Texture rayoTexture, float x, float y) {
        this.texture = texture;
        this.rayoTexture = rayoTexture;
        this.x = x;
        this.y = y;
        this.disparos = new ArrayList<>();
    }

    public void update(float delta) {
        tiempoCambio -= delta;
        if (tiempoCambio <= 0) {
            velocidadX = MathUtils.random(-2f, 2f);
            tiempoCambio = MathUtils.random(1, 3);
        }

        x += velocidadX;

        if (x <= limiteIzquierdo || x + texture.getWidth() >= limiteDerecho) {
            velocidadX *= -1;
        }

        // Disparar aleatoriamente cada 1 a 3 segundos
        tiempoDisparo -= delta;
        if (tiempoDisparo <= 0) {
            disparar();
            tiempoDisparo = MathUtils.random(1, 3);
        }

        // Actualizar los disparos
        Iterator<com.la35D2.game.enemigos.RayoBoss> iter = disparos.iterator();
        while (iter.hasNext()) {
            com.la35D2.game.enemigos.RayoBoss rayo = iter.next();
            rayo.update(delta);
            if (rayo.isEliminado()) {
                iter.remove();
            }
        }
    }

    public void draw(SpriteBatch batch) {
        float escala = 4.0f;
        batch.draw(texture, x, y, texture.getWidth() * escala, texture.getHeight() * escala);

        for (com.la35D2.game.enemigos.RayoBoss rayo : disparos) {
            rayo.draw(batch);
        }
    }

    public Rectangle getBounds() {
        float escala = 4.0f;
        return new Rectangle(x, y, texture.getWidth() * escala, texture.getHeight() * escala);
    }


    public void recibirDisparo() {
        vida--;
        System.out.println("BossJasinski ha recibido un disparo. Vida restante: " + vida);
    }

    public int getVida() {
        return vida;
    }

    public void disparar() {
        float rayoX = x + texture.getWidth() * 2; // Ajuste para centrar el disparo
        float rayoY = y;
        disparos.add(new com.la35D2.game.enemigos.RayoBoss(rayoTexture, rayoX, rayoY));
        System.out.println("¡Boss Jasinski disparó!");
    }

    public List<com.la35D2.game.enemigos.RayoBoss> getDisparos() {
        return disparos;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
