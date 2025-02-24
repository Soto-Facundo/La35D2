package com.la35D2.game.enemigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class FormacionEnemigos {
    private ArrayList<com.la35D2.game.enemigos.Enemigo> enemigos;
    private float velocidad;
    private boolean direccionDerecha;

    public FormacionEnemigos(Texture enemigoTexture, int filas, int columnas, float velocidad) {
        this.velocidad = velocidad / 12;  // Reducir la velocidad a la mitad
        this.direccionDerecha = true;
        enemigos = new ArrayList<>();

        // Crear enemigos en una formación
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                float x = j * 60 + 50;
                float y = i * 60 + 500;
                enemigos.add(new com.la35D2.game.enemigos.Enemigo(x, y, velocidad, enemigoTexture));
            }
        }
    }

    public void update(float delta) {
        if (enemigos.isEmpty()) {
            return; // No hacer nada si la lista está vacía
        }

        // Mover todos los enemigos según la dirección
        for (com.la35D2.game.enemigos.Enemigo enemigo : enemigos) {
            enemigo.setX(enemigo.getX() + (direccionDerecha ? 1 : -1) * velocidad);
            enemigo.update(delta);
        }

        // Cambiar dirección si alcanzan el borde
        if (direccionDerecha) {
            if (enemigos.get(enemigos.size() - 1).getX() > 950) {
                direccionDerecha = false;
                moveDown();
            }
        } else {
            if (enemigos.get(0).getX() < 50) {
                direccionDerecha = true;
                moveDown();
            }
        }
    }

    private void moveDown() {
        for (com.la35D2.game.enemigos.Enemigo enemigo : enemigos) {
            enemigo.setY(enemigo.getY() - 20);  // Baja la fila
        }
    }

    public void draw(SpriteBatch batch) {
        for (com.la35D2.game.enemigos.Enemigo enemigo : enemigos) {
            enemigo.draw(batch);
        }
    }

    public ArrayList<com.la35D2.game.enemigos.Enemigo> getListaEnemigos() {
        return enemigos;
    }


}
