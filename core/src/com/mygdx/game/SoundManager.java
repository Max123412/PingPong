package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class SoundManager {
    Sound bounceSound1, bounceSound2, bounceSound3, loseBallSound;

    void loadSounds(){
        bounceSound1 = Gdx.audio.newSound(Gdx.files.internal("bounce1.ogg"));
        bounceSound2 = Gdx.audio.newSound(Gdx.files.internal("bounce2.ogg"));
        bounceSound3 = Gdx.audio.newSound(Gdx.files.internal("bounce3.ogg"));
        loseBallSound= Gdx.audio.newSound(Gdx.files.internal("lose_ball.ogg"));

    }
    void dispose(){
        bounceSound1.dispose();
        bounceSound2.dispose();
        bounceSound3.dispose();
        loseBallSound.dispose();
    }
    void playRandomBounceSound(){
        Random random = new Random();
        int randomNum = random.nextInt(3);
        if(randomNum == 0){
            bounceSound1.play();
        }
        if(randomNum == 1){
            bounceSound2.play();
        }
        if(randomNum == 2){
            bounceSound3.play();
        }
    }
}
