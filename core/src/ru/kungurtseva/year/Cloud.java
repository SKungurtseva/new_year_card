package ru.kungurtseva.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.kungurtseva.year.YearGame.*;

public class Cloud {
    private static Texture texture;
    private Vector2 position;
    private float speed;
    private float size;
    private  float time;

    private float MIN_SPEED = 20.0f;
    private float MAX_SPEED = 80.0f;

    public float getRandomXPoint() {
        float koef = 0.05f * MathUtils.sin(time) * 1.5f;
        return MathUtils.random(position.x - texture.getWidth()/2 * koef * 1.5f, position.x + texture.getWidth()/2 * koef * 1.5f);
    }

    public float getYPoint() {
        return position.y + texture.getHeight() /2 * 1.5f * 0.3f;
    }

    public Cloud() {
        if (texture == null) {
            texture = new Texture("cloud.png");
        }
        this.position = new Vector2(WIDTH, HEIGHT - 250 + MathUtils.random(100));
        this.size = MathUtils.random(50.0f, 300.0f);
        speed = MathUtils.random(MIN_SPEED, MAX_SPEED);
        this.time = MathUtils.random(360.0f);
    }

    public void render(SpriteBatch batch) {
        float koef = 0.05f * MathUtils.sin(time) * 1.8f; // сжимаем и растягиваем изображение одновременно
        float color = 0.8f - Math.abs(koef);
        batch.setColor(color, color, color, 1);
        batch.draw(texture, position.x-texture.getWidth() / 2, position.y,texture.getWidth() / 2, 0, texture.getWidth(), texture.getHeight(), 1+ koef, 1, 0,0, 0, texture.getWidth(), texture.getHeight(),false, false);
        batch.setColor(1, 1, 1, 1);
    }

    public void update(float dt) {
        time += dt;
        position.x -= speed * dt;
        if (position.x < -texture.getWidth()) {
            position.x = WIDTH + 140;
            speed = MathUtils.random(MIN_SPEED, MAX_SPEED);
            size = MathUtils.random(50.0f, 300.0f);
        }
    }
}
