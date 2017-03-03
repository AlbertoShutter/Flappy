package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Flappy;
import com.mygdx.game.sprites.Bird;

/**
 * Created by alber on 15/02/2017.
 */

public class PlayState extends State {
    private static final int GROUND_Y_OFFSET = -20;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Texture gameoverImg;
    private Vector2 groundPos1;
    private Vector2 groundPos2;
    private Music music;

    private boolean gameover;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(20, 260);
        cam.setToOrtho(false, Flappy.WIDTH / 2, Flappy.HEIGHT / 2) ;
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        gameoverImg = new Texture("gameover.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);
        gameover = false;

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            if(gameover) {
                gsm.set(new PlayState(gsm));
            } else {
                bird.jump();
            }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        cam.position.x = bird.getPosition().x +80;

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameover = true;
            bird.colliding = true;
        }

        cam.update();
    }

    private void updateGround(){
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        if(gameover)
            sb.draw(gameoverImg, cam.position.x - gameoverImg.getWidth() / 2, cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        System.out.println("Play State Disposed");
    }
}