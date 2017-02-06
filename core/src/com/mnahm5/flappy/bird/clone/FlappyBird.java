package com.mnahm5.flappy.bird.clone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import org.w3c.dom.Text;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;

    private Texture[] birds;
    private int flapState = 0;
    private float birdY = 0;
    private float velocity = 0;
    private Circle birdCircle;

    private int gameState = 0;
    private float gravity = 2;

    private Texture topTube;
    private Texture bottomTube;
    private float gap = 1600;
    private float maxTubeOffset;
    private Random randomGenerator;;
    private float tubeVelocity = 12;
    private int noOfTubes = 4;
    private float[] tubeX = new float[noOfTubes];
    private float[] tubeOffset = new float[noOfTubes];
    private float distanceBetweenTubes;

    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        background = new Texture("bg.png");
        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;
        birdCircle = new Circle();

        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - 100;
        randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

        for (int i = 0; i < noOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * maxTubeOffset;
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distanceBetweenTubes;
        }

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
            for (int i = 0; i < noOfTubes; i++) {
                batch.draw(
                        topTube,
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 - topTube.getHeight() / 2 + gap / 2 + tubeOffset[i]
                );
                batch.draw(
                        bottomTube,
                        tubeX[i],
                        Gdx.graphics.getHeight() / 2 - bottomTube.getHeight() / 2 - gap / 2 + tubeOffset[i]
                );
            }
        }

        batch.draw(
                birds[flapState],
                Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
                birdY
        );
        batch.end();

        birdCircle.set(
                Gdx.graphics.getWidth() / 2,
                birdY + birds[flapState].getHeight() / 2,
                birds[flapState].getWidth() / 2
        );
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
        shapeRenderer.end();
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
            }

            if (birdY > 0 || velocity < 0) {
                velocity ++;
                birdY -= velocity + gravity;
            }

            for (int i = 0; i < noOfTubes; i++) {

                if (tubeX[i] <= -topTube.getWidth()) {
                    tubeX[i] += noOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * maxTubeOffset;
                }
                else {
                    tubeX[i] -= tubeVelocity;
                }
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
