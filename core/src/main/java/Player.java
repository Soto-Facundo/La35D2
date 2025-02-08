package com.la35D2.game;

import com.badlogic.gdx.graphics.Texture;
import com.la35D2.game.Jugador.NaveJugador;

public class Player {
    private String name;
    private Texture texture;
    private float x, y;
    private NaveJugador naveJugador;  // Campo para almacenar la nave del jugador

    public Player(String name, String texturePath, float x, float y, NaveJugador naveJugador) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.texture = new Texture(texturePath); // Cargar la textura
        this.naveJugador = naveJugador;  // Almacenar la nave del jugador
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

    public void dispose() {
        texture.dispose();
        naveJugador.dispose();  // Asegúrate de que la nave también se libere al finalizar
    }
}
