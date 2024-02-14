package ru.kungurtseva.year;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.kungurtseva.year.YearGame.*;

public class Moon {
    private Texture texture;
    private Vector2 position;
    private  float time;

    public Moon () {
        this.texture = new Texture("moon.png");
        this.position = new Vector2(WIDTH - 260, HEIGHT - 210);
    }

    public void render(SpriteBatch batch) {
        float koef = 0.1f * MathUtils.sin(time);
        batch.draw(texture, position.x, position.y, 100, 0, 200, 200, 1 + koef, 1, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false); // если луна большая, то можем уменьшить ее до размера 200*200
    }

    public void update(float dt) {
        time += dt;
    }
}
