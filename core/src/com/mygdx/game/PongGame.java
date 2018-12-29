package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PongGame extends ApplicationAdapter {
	SpriteBatch batch;
	SoundManager soundManager;
	Ball ball;
	Paddle paddle;
	int score;
	BitmapFont font;
	final int CATCH_BALL_BONUS = 100;
	final int INITIAL_LIVES_COUNT = 3;
	int livesCounter = INITIAL_LIVES_COUNT;
	Texture gameOverLogoTexture;
	boolean isGameOver;
	Button closeBtn, replayBtn;


	@Override
	public void create () {
		batch = new SpriteBatch();
		ball = new Ball();
		ball.loadTexture();
		paddle = new Paddle();
		paddle.loadTexture();
		paddle.center();
		ball.restart(paddle);
		soundManager = new SoundManager();
		soundManager.loadSounds();
		font = new BitmapFont();
		font.getData().setScale(5);
		gameOverLogoTexture = new Texture("game_over_logo.jpg");
		closeBtn = new Button("close_btn.png");
		closeBtn.x = Gdx.graphics.getWidth() - closeBtn.texture.getWidth();
		replayBtn = new Button("replay_btn.png");
	}

	@Override
	public void render () {
		if(isGameOver){
			if (closeBtn.isClicked()) {
				System.exit(0);
			}
			if (replayBtn.isReleased()) {
				score = 0;
				livesCounter = INITIAL_LIVES_COUNT;
				isGameOver = false;
				paddle.center();
				ball.restart(paddle);
				ball.velocityY = ball.INITIAL_SPEED;
				ball.velocityX = ball.INITIAL_SPEED;
			}
		}

		if(! isGameOver){
			paddle.move();
		}

		if(ball.y + ball.texture.getHeight() < 0){//если мяч проваливается
			ball.restart(paddle);
			soundManager.loseBallSound.play();
			livesCounter --;

			if(livesCounter == 0){
				isGameOver = true;
			}
		}
		if(! isGameOver){
			ball.move(paddle);
		}

		ball.speedUpIfNeeded();
		bounceBall();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		ball.draw(batch);
		paddle.draw(batch);
		font.draw(batch,"Score: " + score +"  Lives: " + livesCounter , 0, Gdx.graphics.getHeight());
       	if(isGameOver){
       		batch.draw(gameOverLogoTexture, (Gdx.graphics.getWidth() - gameOverLogoTexture.getWidth())/2,
				(Gdx.graphics.getHeight() - gameOverLogoTexture.getHeight())/2);
			closeBtn.draw(batch);
			replayBtn.draw(batch);
	   	}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		ball.dispose();
		paddle.dispose();
		soundManager.dispose();
		font.dispose();
		gameOverLogoTexture.dispose();
		closeBtn.dispose();
		replayBtn.dispose();
	}

	void bounceBall(){
		//мяч сталкивается с правой стенкой
		if(ball.x > Gdx.graphics.getWidth() - ball.texture.getWidth()){
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}
		//мяч сталкивается с верхней стенкой
		if(ball.y > Gdx.graphics.getHeight()- ball.texture.getHeight()){
			ball.velocityY = -ball.velocityY;
			soundManager.playRandomBounceSound();
		}
		//мяч сталкивается с левой стенкой
		if(ball.x < 0){
			ball.velocityX = -ball.velocityX;
			soundManager.playRandomBounceSound();
		}
		//мяч сталкивается с верхом битки
		if(ball.x > paddle.x - ball.texture.getWidth() / 2
				&& ball.x < paddle.x - ball.texture.getWidth() / 2 + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight() && ball.y > paddle.y - ball.texture.getHeight()){
				ball.velocityY = -ball.velocityY;
				soundManager.playRandomBounceSound();
				score += CATCH_BALL_BONUS * Math.abs(ball.velocityX) ;
			}
		}
		//мяч отскакивает от левого края битки
		if(ball.x > paddle.x - ball.texture.getWidth() && ball.x < paddle.x - ball.texture.getWidth() / 2 + 1){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX > 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
		//мяч отскакивает от правого края битки
		if(ball.x > paddle.x + paddle.texture.getWidth() - ball.texture.getWidth() / 2 - 1
				&& ball.x < paddle.x + paddle.texture.getWidth()){
			if(ball.y < paddle.y + paddle.texture.getHeight()){
				if(ball.velocityX < 0) {
					ball.velocityX = -ball.velocityX;
					soundManager.playRandomBounceSound();
				}
			}
		}
	}
}