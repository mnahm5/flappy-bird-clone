package com.mnahm5.flappy.bird.clone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.w3c.dom.Text;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;

    private Texture[] birds;
    private int flapState = 0;
    float birdY = 0;
    float velocity = 0;

    int gameState = 0;

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;
    }

    @Override
    public void render () {
        Update();
        batch.begin();
        batch.draw(
                background,
                0, 0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        batch.draw(
                birds[flapState],
                Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
                birdY
        );
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        background.dispose();
        birds[0].dispose();
        birds[1].dispose();
    }

    private void Update()
    {
        if (gameState != 0) {
            velocity ++;
            birdY -= velocity;
        }
        else {
            if (Gdx.input.justTouched()) {
                Gdx.app.log("Touched", "True");
                gameState = 1;
            }
        }

        if (flapState == 0) {
            flapState = 1;
        }
        else {
            flapState = 0;
        }
    }
}
