package ru.kungurtseva.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Toys {
    private Texture texture;
    private Vector2 position;

    public Toys () {
        this.texture = new Texture("toys.png");
        this.position = new Vector2(20, 25);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 100, 0, 200, 200, 1.0f, 1.0f, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
