package com.la35D2.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;


public class La35D2 extends Game {
    private SpriteBatch batch;
    private Texture menuImage;  // Para la imagen del menú
    private BitmapFont font;     // Para mostrar texto

    private float playButtonX, playButtonY, playButtonWidth, playButtonHeight;

    @Override
    public void create() {
        batch = new SpriteBatch();
        menuImage = new Texture("MenuFoto.jpg");  // Carga la imagen del menú desde la carpeta assets

        // Inicializamos la fuente para los textos
        font = new BitmapFont();
        font.setColor(Color.WHITE); // Color blanco para el texto

        // Inicializamos las coordenadas y tamaños del botón "Play"
        playButtonWidth = 400; // Doblamos el tamaño del botón
        playButtonHeight = 100;
        playButtonX = Gdx.graphics.getWidth() / 2 - playButtonWidth / 2 + 75; // Mover 50 píxeles a la derecha
        playButtonY = Gdx.graphics.getHeight() / 2 - 155; // Ajustamos la posición para mover los botones hacia abajo

        // Cambiar la pantalla a MainMenuScreen
        this.setScreen(new com.la35D2.game.MainMenuScreen(this));  // Pasamos 'this' para poder acceder a la instancia de 'La35D2' si es necesario
    }

    @Override
    public void dispose() {
        batch.dispose();
        menuImage.dispose();
        font.dispose();  // Liberamos la memoria de la fuente
    }
}
