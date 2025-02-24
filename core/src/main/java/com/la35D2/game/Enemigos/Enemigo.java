package com.la35D2.game.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;


public class Enemigo {
    private float x, y;
    private float speed;
    private Texture texture;
    private float scale;  // Factor de escala para ajustar el tamaño

    public Enemigo(float x, float y, float speed, Texture texture) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = texture;
        this.scale = 0.1f;  // Establecer una escala del 10% (puedes ajustar esto)
    }


    public void update(float delta) {
        x += speed * delta;
    }

    public void draw(SpriteBatch batch) {
        // Dibujar la textura con la escala aplicada
        batch.draw(texture, x, y, texture.getWidth() * scale, texture.getHeight() * scale);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, texture.getWidth() * scale, texture.getHeight() * scale);
    }

}
