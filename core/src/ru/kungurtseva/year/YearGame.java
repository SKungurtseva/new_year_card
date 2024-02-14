package ru.kungurtseva.year;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;

public class YearGame extends ApplicationAdapter {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 800;
    private SpriteBatch batch;
    private PolygonSpriteBatch polygonSpriteBatch;
    private Snow[] snows;
    private Moon moon;
    private Cloud[] clouds;
    private House house;
    private Father_Christmas hero;
    private Toys toys;
    private Texture edgeTexture;
    private PolygonSprite[] grounds;
    private Music music;


    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.polygonSpriteBatch = new PolygonSpriteBatch();
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        this.music.setLooping(true); // зацикливаю музыку
        this.music.play();
        //this.music.stop();
        this.clouds = new Cloud[8];
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }
        this.snows = new Snow[600];
        for (int i = 0; i < snows.length; i++) {
            snows[i] = new Snow(clouds);
        }
        this.moon = new Moon();
        this.hero = new Father_Christmas();
        this.house = new House();
        this.toys = new Toys();
        this.edgeTexture = new Texture("ball.png");
        this.grounds = new PolygonSprite[]{
                generatePolySprite(360, 140, 0.1f),
                generatePolySprite(240, 80, 0.7f),
                generatePolySprite(160, 60, 1.0f)
        };
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        ScreenUtils.clear(0.15f, 0.16f, 0.37f, 1);
        batch.begin();
        moon.render(batch);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (int i = 0; i < snows.length; i++) {
            snows[i].render(batch);
        }
        polygonSpriteBatch.begin();
        for (int i = 0; i < grounds.length; i++) {
            grounds[i].draw(polygonSpriteBatch);
        }
        polygonSpriteBatch.end();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        toys.render(batch);
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].render(batch);
        }
        house.render(batch);
        hero.render(batch);

        for (int i = 0; i < WIDTH / 40 + 1; i++) {
            batch.draw(edgeTexture, i * 40 - 20, -20);
            batch.draw(edgeTexture, i * 40 - 20, HEIGHT - 20);
        }
        for (int i = 0; i < HEIGHT / 40 + 1; i++) {
            batch.draw(edgeTexture, -20, i * 40 - 20);
            batch.draw(edgeTexture, WIDTH - 20, i * 40 - 20);
        }
        batch.end();
    }

    public void update(float dt) {
        for (int i = 0; i < snows.length; i++) {
            snows[i].update(dt);
        }
        moon.update(dt);
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].update(dt);
        }
        hero.update(dt);
    }

    @Override
    public void dispose() { //освобождение ресурсов, вместо сборщика мусора
        batch.dispose();
    }

    private void getCenter(float[] array, int left, int right, int rnd) {
        int mid = (left + right) / 2;
        array[mid] = (array[left] + array[right]) / 2 + MathUtils.random(-rnd, rnd);
        if (right - left > 1) {
            if (rnd > 2) {
                rnd /= 2;
            }
            getCenter(array, left, mid, rnd);
            getCenter(array, mid, right, rnd);
        }
    }

    private PolygonSprite generatePolySprite(float baseHeight, int randomHeight, float colorFading) { // PolygonSprite - это пачка вершин соединенных между собой и имеющая какую то текстуру (цвет).
        int BLOCK_WIDTH = 5;
        int BLOCKS_COUNT = WIDTH / BLOCK_WIDTH + 5;

        float[] vertices = new float[BLOCKS_COUNT * 4]; // вершины полигонов
        short[] indices = new short[BLOCKS_COUNT * 6]; // индексы вершин треугольников для их соединения между собой

        float[] heightMap = new float[BLOCKS_COUNT];
        heightMap[0] = baseHeight + MathUtils.random(-50, 50);
        heightMap[heightMap.length - 1] = baseHeight + MathUtils.random(-50, 50);

        getCenter(heightMap, 0, heightMap.length - 1, randomHeight);

        float maxHeight = 0;
        for (int i = 0; i < heightMap.length; i++) {
            if (heightMap[i] > maxHeight) {
                maxHeight = heightMap[i];
            }
        }

        for (int i = 0; i < BLOCKS_COUNT; i++) {
            vertices[i * 4 + 0] = i * BLOCK_WIDTH;
            vertices[i * 4 + 1] = 0;
            vertices[i * 4 + 2] = i * BLOCK_WIDTH;
            vertices[i * 4 + 3] = heightMap[i];
        }

        for (int i = 0; i < BLOCKS_COUNT - 1; i++) {
            indices[i * 6 + 0] = (short) (i * 2 + 0);
            indices[i * 6 + 1] = (short) (i * 2 + 1);
            indices[i * 6 + 2] = (short) (i * 2 + 2);
            indices[i * 6 + 3] = (short) (i * 2 + 1);
            indices[i * 6 + 4] = (short) (i * 2 + 3);
            indices[i * 6 + 5] = (short) (i * 2 + 2);
        }

        Pixmap pix = new Pixmap(2, (int) maxHeight, Pixmap.Format.RGBA8888); // формируем текстуру по нашей макс высоте длиной 2 пикселя белого цвета
        for (int i = 0; i < maxHeight; i++) {
            float c = (1.0f - 1 / maxHeight) * colorFading;
            pix.setColor(c, c, c, 1.0f);
            pix.fillRectangle(0, i * 4, 2, 4);
        }
        TextureRegion textureRegion = new TextureRegion(new Texture(pix));
        return new PolygonSprite(new PolygonRegion(textureRegion, vertices, indices));
    }
}











