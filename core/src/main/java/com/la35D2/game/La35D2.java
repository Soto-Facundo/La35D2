package com.la35D2.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.la35D2.game.Jugador.NaveJugador;

public class La35D2 extends Game {
    private SpriteBatch batch;
    private Texture menuImage;  // Para la imagen del menú
    private BitmapFont font;     // Para mostrar texto

    private float playButtonX, playButtonY, playButtonWidth, playButtonHeight;

    private com.la35D2.game.Player selectedPlayer;  // Nueva variable para guardar el jugador seleccionado

    @Override
    public void create() {
        batch = new SpriteBatch();
        menuImage = new Texture("MenuFoto.jpg");  // Carga la imagen del menú desde la carpeta assets

        // Inicializamos la fuente para los textos
        font = new BitmapFont();
        font.setColor(Color.WHITE); // Color blanco para el texto

        // Inicializamos las coordenadas y tamaños del botón "Play"
        playButtonWidth = 400;
        playButtonHeight = 100;
        playButtonX = Gdx.graphics.getWidth() / 2 - playButtonWidth / 2 + 75;
        playButtonY = Gdx.graphics.getHeight() / 2 - 155;

        // Cargar texturas para las naves
        Texture texturaQuispe = new Texture("QuispePixel.png");
        Texture texturaSoto = new Texture("SotoPixel.png");

        // Crear las naves para los jugadores con la textura correspondiente
        NaveJugador naveQuispe = new NaveJugador(400, 50, texturaQuispe);
        NaveJugador naveSoto = new NaveJugador(400, 50, texturaSoto);

        // Crear los jugadores
        com.la35D2.game.Player playerQuispe = new com.la35D2.game.Player("Quispe", "QuispePixel.png", 400, 50, naveQuispe);
        com.la35D2.game.Player playerSoto = new com.la35D2.game.Player("Soto", "SotoPixel.png", 400, 50, naveSoto);

        // Configurar el jugador seleccionado (ejemplo con Quispe)
        setSelectedPlayer(playerQuispe);

        // Cambiar la pantalla a MainMenuScreen
        this.setScreen(new com.la35D2.game.MainMenuScreen(this));
    }

    // Método para establecer el jugador seleccionado
    public void setSelectedPlayer(com.la35D2.game.Player player) {
        this.selectedPlayer = player;
    }

    // Método para obtener el jugador seleccionado
    public com.la35D2.game.Player getSelectedPlayer() {
        return selectedPlayer;
    }

    @Override
    public void dispose() {
        batch.dispose();
        menuImage.dispose();
        font.dispose();
    }
}
