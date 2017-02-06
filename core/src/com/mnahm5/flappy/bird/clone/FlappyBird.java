package com.mnahm5.flappy.bird.clone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;

import org.w3c.dom.Text;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;

    private Texture[] birds;
    private int flapState = 0;
    private float birdY = 0;
    private float velocity = 0;

    private int gameState = 0;
    private float gravity = 2;

    private Texture topTube;
    private Texture bottomTube;
    private float gap = 1600;
    private float maxTubeOffset;
    private Random randomGenerator;
    private float tubeOffset;
    private float tubeVelocity = 12;
    private float tubeX;

    @Override
    public void create () {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - 100;
        randomGenerator = new Random();
        tubeX = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2;
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

        if (gameState != 0) {
            batch.draw(
                    topTube,
                    tubeX,
                    Gdx.graphics.getHeight() / 2 - topTube.getHeight() / 2 + gap / 2 + tubeOffset
            );

            batch.draw(
                    bottomTube,
                    tubeX,
                    Gdx.graphics.getHeight() / 2 - bottomTube.getHeight() / 2 - gap / 2 + tubeOffset
            );

            tubeX -= tubeVelocity;
        }

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
            if (Gdx.input.justTouched()) {
                velocity = -20;

                tubeOffset = (randomGenerator.nextFloat() - 0.5f) * maxTubeOffset;
                tubeX = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2;
            }

            if (birdY > 0 || velocity < 0) {
                velocity ++;
                birdY -= velocity + gravity;
            }
        }
        else {
            if (Gdx.input.justTouched()) {
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
