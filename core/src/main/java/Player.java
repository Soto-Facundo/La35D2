package com.la35D2.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.la35D2.game.Jugador.NaveJugador;

public class Player {
    private String name;
    private Texture texture;
    private float x, y;
    private NaveJugador naveJugador;  // Campo para almacenar la nave del jugador

    // Velocidad de movimiento (puedes ajustarla según lo necesites)
    private float speed = 50f;  // Ajusta la velocidad según lo que necesites

    public Player(String name, String texturePath, float x, float y, NaveJugador naveJugador) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.texture = new Texture(texturePath); // Cargar la textura
        this.naveJugador = naveJugador;  // Almacenar la nave del jugador
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

    public void setTexture(Texture texture) {
        this.texture = texture;
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

    // Actualización de la posición del jugador
    public void update(float delta) {
        // Movimiento con las teclas de flecha
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * delta;  // Mueve hacia arriba
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * delta;  // Mueve hacia abajo
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * delta;  // Mueve hacia la izquierda
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * delta;  // Mueve hacia la derecha
        }
    }

    public void dispose() {
        texture.dispose();
        naveJugador.dispose();  // Asegúrate de que la nave también se libere al finalizar
    }
}
