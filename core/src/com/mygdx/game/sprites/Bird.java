package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {/**

 * Created by alber on 15/02/2017.
 */

private static final int GRAVITY = -50;
    private static final int MOVEMENT = 200;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Texture texture;
    private Animation catAnimation;

    public boolean colliding;

    public Bird(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("birdanimation.png");
        catAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        colliding = false;
    }

    public void update(float dt){
        catAnimation.update(dt);
        velocity.add(0, GRAVITY, 0);
        velocity.scl(dt);
        if(!colliding)
            position.add(MOVEMENT * dt, velocity.y, 0);
        if(position.y < 82)
            position.y = 82;

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump(){
        velocity.y = 200;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return catAnimation.getFrame();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
    }
}
