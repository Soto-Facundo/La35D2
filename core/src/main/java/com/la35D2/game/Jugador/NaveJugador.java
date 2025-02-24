package com.la35D2.game.Jugador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.la35D2.game.Globales;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class NaveJugador {
    private Sprite sprite;
    private Vector2 position;
    private float speed = 500f;
    private List<RayoJugador> rayos;
    public NaveJugador(float x, float y, Texture texture) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        sprite.setPosition(position.x, position.y);
        rayos = new ArrayList<>();
    }
    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        sprite.setPosition(x, y);
    }
    public void disparar(Texture texturaRayo) {
        System.out.println("Método disparar() ejecutado");
        RayoJugador nuevoRayo = new RayoJugador(position.x + sprite.getWidth() / 2, position.y, texturaRayo);
        rayos.add(nuevoRayo);
        System.out.println("Disparo creado en X: " + position.x + " Y: " + position.y);
    }
    public void update(float delta) {
        for (RayoJugador rayo : rayos) {
            rayo.update(delta);
        }
    }
    public void draw() {
        sprite.draw(Globales.batch);
        for (RayoJugador rayo : rayos) {

            rayo.draw();
        }
    }
    public boolean isOffScreen() {
        return position.y > 480;
    }
    public void dispose() {
        sprite.getTexture().dispose();
        for (RayoJugador rayo : rayos) {
            rayo.dispose();
        }
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

}
