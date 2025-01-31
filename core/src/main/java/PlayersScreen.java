package com.la35D2.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Input;

public class PlayersScreen implements Screen {
    private La35D2 game;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private BitmapFont font;
    private Player quispe;
    private Player soto;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float VIRTUAL_WIDTH = 800;  // Ancho virtual fijo
    private static final float VIRTUAL_HEIGHT = 480; // Alto virtual fijo

    public PlayersScreen(La35D2 game) {
        this.game = game;
    }

    @Override
    public void show() {
        // Inicializar la cámara y el viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();
        backgroundImage = new Texture("PlayersBackground.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);

        // Inicializar a los jugadores y sus texturas
        quispe = new Player("Quispe", "Quispe.Player2.png", VIRTUAL_WIDTH / 2 - 200, 200);
        soto = new Player("Soto", "Soto.Player1.png", VIRTUAL_WIDTH / 2 + 50, 200);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Configurar la cámara
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundImage, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        font.draw(batch, "Seleccione a su jugador", VIRTUAL_WIDTH / 2 - 100, 400);

        // Usar directamente la ruta de la textura en vez del método getTexture()
        Texture quispeTexture = new Texture(quispe.getName() + ".Player2.png"); // Usar la ruta directamente
        Texture sotoTexture = new Texture(soto.getName() + ".Player1.png"); // Usar la ruta directamente
        batch.draw(quispeTexture, quispe.getX(), quispe.getY(), 150, 150); // Reducción al 50%
        batch.draw(sotoTexture, soto.getX(), soto.getY(), 150, 150); // Reducción al 50%

        // Dibujar nombres debajo de las imágenes
        font.draw(batch, quispe.getName(), quispe.getX() + 25, 180);
        font.draw(batch, soto.getName(), soto.getX() + 25, 180);

        batch.end();

        // Detectar clics en los nombres de los jugadores
        detectPlayerClick();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        font.dispose();
        quispe.dispose();
        soto.dispose();
    }

    // Método para detectar clics en los nombres de los jugadores
    private void detectPlayerClick() {
        // Detectar clic sobre el nombre de Quispe
        float xQuispe = quispe.getX() + 25;
        float yQuispe = 180;
        float widthQuispe = font.getRegion().getRegionWidth();  // Usar getRegionWidth
        float heightQuispe = font.getLineHeight();  // Esto te da la altura de la línea del texto

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            mouseY = Gdx.graphics.getHeight() - mouseY; // Ajustar coordenada Y debido a la inversión de ejes

            if (mouseX >= xQuispe && mouseX <= xQuispe + widthQuispe &&
                mouseY >= yQuispe - heightQuispe && mouseY <= yQuispe) {
                game.setScreen(new GameMapScreen(game)); // Cambiar a la pantalla del juego
            }
        }

        // Detectar clic sobre el nombre de Soto
        float xSoto = soto.getX() + 25;
        float ySoto = 180;
        float widthSoto = font.getRegion().getRegionWidth();  // Usar getRegionWidth
        float heightSoto = font.getLineHeight();  // Esto te da la altura de la línea del texto

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            mouseY = Gdx.graphics.getHeight() - mouseY; // Ajustar coordenada Y debido a la inversión de ejes

            if (mouseX >= xSoto && mouseX <= xSoto + widthSoto &&
                mouseY >= ySoto - heightSoto && mouseY <= ySoto) {
                game.setScreen(new GameMapScreen(game)); // Cambiar a la pantalla del juego
            }
        }
    }
}
