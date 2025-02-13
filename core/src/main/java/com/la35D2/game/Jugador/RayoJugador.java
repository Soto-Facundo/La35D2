package com.la35D2.game.Jugador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.la35D2.game.Globales;

public class RayoJugador {
    private Sprite sprite;
    private Vector2 position;
    private float speed = 300f;

    public RayoJugador(float x, float y, Texture texture) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        sprite.setPosition(position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float delta) {
        position.y += speed * delta;
        sprite.setPosition(position.x, position.y);
    }

    public void draw() {
        sprite.draw(Globales.batch);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
