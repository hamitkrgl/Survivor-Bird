	package com.example.hamit.survivorbird;
	import com.badlogic.gdx.ApplicationAdapter;
	import com.badlogic.gdx.Gdx;
	import com.badlogic.gdx.graphics.Color;
	import com.badlogic.gdx.graphics.GL20;
	import com.badlogic.gdx.graphics.Texture;
	import com.badlogic.gdx.graphics.g2d.BitmapFont;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
	import com.badlogic.gdx.math.Circle;
	import com.badlogic.gdx.math.Intersector;
	import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
	import java.util.Random;
	public class survivorbird extends ApplicationAdapter {

		SpriteBatch batch;
		Texture background;
		Texture bird;
		Texture bee1;
		Texture bee2;
		Texture bee3;

		float birdX=0;
		float birdY=0;

		int gameState=0;
		float velocity=0;
		float gravity=0.1f;

		float enemyVelocity=2;
		Random rndm;
		int score=0;
		int scoreEnemy=0;
		BitmapFont font;
		BitmapFont font2;

		Circle birdCircle;
		ShapeRenderer shapeRenderer;

		int numberOfEnemies=4;
		float [] enemyX=new float[numberOfEnemies];
		float [] enemyOffset=new float[numberOfEnemies];
		float [] enemyOffset2=new float[numberOfEnemies];
		float [] enemyOffset3=new float[numberOfEnemies];
		float distance=0;

		Circle[] enemyCircle;
		Circle[] enemyCircle2;
		Circle[] enemyCircle3;

		@Override
		public void create () {

		batch=new SpriteBatch();
		background=new Texture("mountain.png");
		bird=new Texture("bird.png");
		bee1=new Texture("bee.png");
		bee2=new Texture("bee.png");
		bee3=new Texture("bee.png");

		shapeRenderer=new ShapeRenderer();

		birdX=Gdx.graphics.getWidth()/2-bird.getHeight()/2;
		birdY=Gdx.graphics.getHeight()/3;

		birdCircle=new Circle();
		enemyCircle=new Circle[numberOfEnemies];
		enemyCircle2=new Circle[numberOfEnemies];
		enemyCircle3=new Circle[numberOfEnemies];
		distance=Gdx.graphics.getWidth();
		rndm=new Random();
		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2=new BitmapFont();
		font2.setColor(Color.WHITE);
		font.getData().setScale(6);



		for (int i=0;i<numberOfEnemies;i++)
		{
			enemyOffset[i]=(rndm.nextFloat())*(Gdx.graphics.getHeight());
			enemyOffset2[i]=(rndm.nextFloat())*(Gdx.graphics.getHeight());
			enemyOffset3[i]=(rndm.nextFloat())*(Gdx.graphics.getHeight());
			enemyX[i]=Gdx.graphics.getWidth()-bee1.getWidth()/2*i*50;

			enemyCircle[i]=new Circle();
			enemyCircle2[i]=new Circle();
			enemyCircle3[i]=new Circle();


		}

		}

			@Override
			public void render () {
				batch.begin();
				batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
			if (gameState == 1)
			{
				if(enemyX[scoreEnemy]<Gdx.graphics.getWidth()/2-bird.getHeight()/2)
				{
				score++;
				if(scoreEnemy<numberOfEnemies-1)
				{
					scoreEnemy++;
				}else
				{
					scoreEnemy=0;
				}
				}
				if(Gdx.input.justTouched())
				{
					velocity = -7;
				}
				for(int i=0;i<numberOfEnemies;i++)
				{
					if(enemyX[i]<Gdx.graphics.getWidth()/15)
					{
					enemyX[i]=enemyX[i]+numberOfEnemies*distance;
						enemyOffset[i]=(rndm.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
						enemyOffset2[i]=(rndm.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
						enemyOffset3[i]=(rndm.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					}else
					{
						enemyX[i]=enemyX[i]-enemyVelocity;
					}

					//enemyX[i]=enemyX[i]-enemyVelocity;

					batch.draw(bee1,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
					batch.draw(bee2,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
					batch.draw(bee3,enemyX[i],Gdx.graphics.getHeight()/2+enemyOffset3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

					enemyCircle[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffset[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
					enemyCircle2[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffset2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
					enemyCircle3[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOffset3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				}
				if(birdY>0)
				{
					velocity=velocity+gravity;
					birdY=birdY-velocity;
				}else
				{
					gameState=2;
				}
			}
			else if(gameState==0)
			{
				if(Gdx.input.justTouched())
				{
					gameState = 1;
				}
			}else if(gameState==2)
			{
				font2.draw(batch,"Game over Tap to play again ",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/3);
				if(Gdx.input.justTouched())
				{
					gameState = 1;
					birdY=Gdx.graphics.getHeight()/3;

					for (int i=0;i<numberOfEnemies;i++)
					{
						enemyOffset[i]=(rndm.nextFloat())*(Gdx.graphics.getHeight());
						enemyOffset2[i]=(rndm.nextFloat())*(Gdx.graphics.getHeight());
						enemyOffset3[i]=(rndm.nextFloat())*(Gdx.graphics.getHeight());
						enemyX[i]=Gdx.graphics.getWidth()-bee1.getWidth()/2*i*50;

						enemyCircle[i]=new Circle();
						enemyCircle2[i]=new Circle();
						enemyCircle3[i]=new Circle();
					}
					velocity=0;
					score=0;
					scoreEnemy=0;
				}
			}
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
			font.draw(batch,String.valueOf(score),100,200);
			batch.end();
		birdCircle.set(birdX+Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
		for(int i=0;i<numberOfEnemies;i++)
		{
			if(Intersector.overlaps(birdCircle,enemyCircle[i])||Intersector.overlaps(birdCircle,enemyCircle2[i])||Intersector.overlaps(birdCircle,enemyCircle3[i]))//çarpışmaları anlamak için yapılan kontrol
			{
				gameState=2;
			}
		}
	}
		@Override
		public void dispose () {
		}
	}
