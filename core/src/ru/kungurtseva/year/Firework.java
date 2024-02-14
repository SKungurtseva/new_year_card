package ru.kungurtseva.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Firework {
    private static Texture texture; // одна и та же картинка у всех 200 фейерверков
    private Vector2 position;
    private float speed;
    private boolean alive;
    public boolean isAlive() {
        return alive;
    }

    public Firework() {
        if (texture == null) {
            texture = new Texture("firework.png");
        }
        position = new Vector2(0, 0);
        speed = 400.0f;
        alive = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 395, position.y + 200);
    }

    public void update(float dt) {
        position.y += speed * dt;
        if (position.y > 800) {
            alive = false;
        }
    }

    public void setup(Vector2 position) {
        this.position.set(position);
        alive = true;
    }
}
