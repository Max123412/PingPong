package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ball {
    Texture texture;
    int x, y;
    final int INITIAL_SPEED = 10;
    int velocityX = INITIAL_SPEED, velocityY = INITIAL_SPEED;
    int ballStartFrameCounter;
    int ballFlyFrameCounter;
    final int FRAMES_TO_WAIT_BEFORE_BALL_START = 60;
    void loadTexture(){
        texture = new Texture("ball_small.png");
    }
    void dispose(){
        texture.dispose();
    }
    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }
    void move(Paddle paddle){
        ballStartFrameCounter++;
        // мяч летает
        if(ballStartFrameCounter >= FRAMES_TO_WAIT_BEFORE_BALL_START){
            x += velocityX;
            y += velocityY;
            ballFlyFrameCounter++;
            // если мяч не летает то он ездит вместе с биткой
        }else{
            x = paddle.x + (paddle.texture.getWidth() - texture.getWidth()) / 2;
        }
    }
    void restart(Paddle paddle){
        x = paddle.x + paddle.texture.getWidth() / 2 - texture.getWidth() / 2;
        y = paddle.y + paddle.texture.getHeight();
        ballStartFrameCounter = 0;
        velocityY = Math.abs(velocityY);
    }
    void speedUpIfNeeded(){
        if(ballFlyFrameCounter == 100){
            if(velocityX > 0 ){
                velocityX ++;
            }else{
                velocityX --;
            }if(velocityY > 0 ){
                velocityY ++;
            }else{
                velocityY --;
            }
            ballFlyFrameCounter = 0;
        }
    }
}
