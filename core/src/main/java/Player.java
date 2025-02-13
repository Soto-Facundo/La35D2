package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.la35D2.game.Jugador.NaveJugador;

public class Player {
    private String name;
    private Texture texture;
    private float x, y;
    private NaveJugador naveJugador;

    private float speed = 200f; // Velocidad de movimiento

    public Player(String name, String texturePath, float x, float y, NaveJugador naveJugador) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.texture = new Texture(texturePath);
        this.naveJugador = naveJugador;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public NaveJugador getNaveJugador() {
        return naveJugador;
    }

    public void update(float delta) {
        // Movimiento solo en horizontal (izquierda/derecha)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;
        }

        // Restringir el movimiento dentro de la pantalla
        if (x < 0) x = 0;
        if (x > 950 - texture.getWidth()) x = 950 - texture.getWidth();
    }

    // Llamamos al dispose de la naveJugador para liberar sus recursos
    public void dispose() {
        texture.dispose();
        naveJugador.dispose(); // Aquí es donde ahora liberamos los recursos de la nave
    }
}
