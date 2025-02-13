package com.la35D2.game.Jugador;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.la35D2.game.Globales;
import java.util.ArrayList;
import java.util.List;

public class NaveJugador {
    private Sprite sprite;
    private Vector2 position;
    private float speed = 500f;
    private List<RayoJugador> rayos; // Lista de rayos disparados

    public NaveJugador(float x, float y, Texture texture) {
        this.sprite = new Sprite(texture);
        this.position = new Vector2(x, y);
        sprite.setPosition(position.x, position.y);
        rayos = new ArrayList<>();
    }

    public void disparar(Texture texturaRayo) {
        System.out.println("Método disparar() ejecutado");  // Esto debería imprimir
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
            System.out.println("Dibujando rayo en X: " + rayo.getPosition().x + " Y: " + rayo.getPosition().y);
            rayo.draw();
        }
    }

    public boolean isOffScreen() {
        // Verificar si el rayo se ha ido fuera de la pantalla
        return position.y > 480; // O cualquier valor basado en tu altura de pantalla
    }

    public void dispose() {
        sprite.getTexture().dispose(); // Liberar la textura de la nave
        for (RayoJugador rayo : rayos) {
            rayo.dispose(); // Asegurarse de liberar también los recursos de los rayos
        }
    }
}
