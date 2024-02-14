package ru.kungurtseva.year;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.kungurtseva.year.YearGame.HEIGHT;

public class Father_Christmas {
    private Texture texture;
    private Vector2 position;
    private float speed;
    private float fireTime;
    private float fireRate;
    private Firework[] fireworks;
    private float time;
    private float size;
    private float MIN_SPEED = 80.0f;
    private float MAX_SPEED = 100.0f;


    public Father_Christmas() {
        this.texture = new Texture("Father Christmas.png");
        this.position = new Vector2(-150, HEIGHT - 650 + MathUtils.random(200));
        this.speed = 80;
        this.fireRate = 0.2f;
        this.fireworks = new Firework[50];
        for (int i = 0; i < fireworks.length; i++) {
            fireworks[i] = new Firework();
        }
    }

    public void render(SpriteBatch batch) {
        float koef = 0.05f * MathUtils.sin(time) * 1.8f; // сжимаем и растягиваем изображение одновременно
        batch.draw(texture, position.x - texture.getWidth() / 1.5f, position.y, texture.getWidth() / 1.5f, 0, texture.getWidth(), texture.getHeight(), 1 + koef, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        for (int i = 0; i < fireworks.length; i++) {
            if (fireworks[i].isAlive()) {
                fireworks[i].render(batch);
            }
        }
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireTime += dt;
            if (fireTime > fireRate) {
                fireTime -= fireRate;
                fire();
            }
        }
        for (int i = 0; i < fireworks.length; i++) {
            if (fireworks[i].isAlive()) {
                fireworks[i].update(dt);
            }
        }

        time += dt;
        position.x += speed * dt;
        position.y += speed / 4 * dt;

        if (position.y > texture.getHeight() + 400) {
            position.y = -140;
            speed = MathUtils.random(MIN_SPEED, MAX_SPEED);
            size = MathUtils.random(50.0f, 300.0f);
        }

        if (position.x > texture.getWidth() + 400) {
            position.x = -540;
            position.y = -170 + MathUtils.random(500);

            speed = MathUtils.random(MIN_SPEED, MAX_SPEED);
            size = MathUtils.random(50.0f, 300.0f);
        }
    }

    public void fire() {
        for (int i = 0; i < fireworks.length; i++) {
            if (!fireworks[i].isAlive()) {
                fireworks[i].setup(position);
                return;
            }
        }
    }
}
