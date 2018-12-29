package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
    Texture texture;
    int x, y;
    boolean hasBeenTouched;

    Button(String textureName){
        texture = new Texture(textureName);
    }
    void dispose(){
        texture.dispose();
    }

    void draw(SpriteBatch batch){
        batch.draw(texture, x, y);
    }

    boolean isClicked(){
        if(Gdx.input.justTouched()){
            int touchX = Gdx.input.getX();
            int touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if(touchX >= x && touchX < x + texture.getWidth() && touchY >= y && touchY < y + texture.getHeight() ){
                return true;
            }
        }
        return false;
    }
    boolean isReleased(){
        if(Gdx.input.justTouched()){
            hasBeenTouched = true;
        }

        if(hasBeenTouched && ! Gdx.input.isTouched()){
            hasBeenTouched = false;
            return true;
        }else {
            return  false;
        }
    }
}



