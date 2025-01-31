package com.la35D2.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private String name;
    private Texture texture;
    private float x, y;

    public Player(String name, String texturePath, float x, float y) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public String getName() {
        return name;
    }

    // Métodos para obtener las coordenadas
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void dispose() {
        texture.dispose();
    }
}

