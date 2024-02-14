package ru.kungurtseva.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class House {
    private Texture texture;
    private Vector2 position;

    public House () {
        this.texture = new Texture("house.png");
        this.position = new Vector2(390, 0);
    }

    public void render(SpriteBatch batch) { // отрисовка
        batch.draw(texture, position.x, position.y, 100, 0, 200, 200, 2 , 2, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
