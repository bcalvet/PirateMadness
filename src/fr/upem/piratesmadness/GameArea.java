package fr.upem.piratesmadness;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {
	BattleGround bg;
	Thread workerThread;

	public GameArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		final MainActivity main = (MainActivity) getContext();
		final GameArea ga = this;
		bg = BattleGround.initGame(main);

		//Initialization of the IAController too
		final IAController ia = new IAController();
		final ImpactController impactController = new ImpactController(); 

		final OnTouchListener otl = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.d("PiratesMadness","évènement dans le onTouch");
					int id = -1;
					if(bg.arrayPirates.get(0).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY())){
						if(!bg.arrayPirates.get(1).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY()))
							id = 0;
					} else if(bg.arrayPirates.get(1).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY())){
						if(!bg.arrayPirates.get(0).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY()))
							id = 1;
					}
					if(id!=-1){
						//This condition avoids pirate to jump in fly
						if(!bg.arrayPirates.get(id).noGravity){
							if(bg.arrayPirates.get(id).twiceJump<10){
								bg.arrayPirates.get(id).twiceSpeedAcceleration=true;
							}
							ia.startingToJump(bg.arrayPirates.get(id));
						}
					}
					return true;
				default:
					//Event is consumed to do nothing
					return true;
				}
			}
		};
		this.setOnTouchListener(otl);

		//Initialization of the pirates must be done before this Thread due to the destruction of this thread

		workerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				//Initialization of gravity and direction
				//				impactController.update(bg);
				long time = System.currentTimeMillis();
				for(int i=0; i<bg.arrayPirates.size(); i++){
					Pirate pirate = bg.arrayPirates.get(i);
					if(pirate.gravity==null)
						pirate.gravity=Direction.SOUTH;
					pirate.gravity.randomDirection(pirate);
				}
				//Infinity cycle to move and update information
				while(!Thread.currentThread().isInterrupted()){
					SurfaceHolder holder = ga.getHolder();
					impactController.update(bg);
					ia.update(bg);
					if(Bonus.generate()){
						ga.bg.arrayBonus.add(Bonus.generator(ga));
					}
					long current_time = System.currentTimeMillis()-time;
					try{
						Canvas canvas = holder.lockCanvas();
						canvas.drawRGB(255, 255, 255);
						drawMap(canvas);
						drawPirate(canvas);
						drawBonus(canvas);
						holder.unlockCanvasAndPost(canvas);
					}catch(NullPointerException npe){
						//Do Nothing
					}
					boolean dead = false;
					for(int i = 0; i<bg.arrayPirates.size();i++)
						dead |= bg.arrayPirates.get(i).life<=0;
					if(current_time>=100000 || dead) 
						gameOver(bg.arrayPirates, Math.min(current_time, 100000));
				}
			}
		});
		workerThread.setDaemon(true);
	}

	private void gameOver(ArrayList<Pirate> pirates, long time) {
		final MainActivity main = (MainActivity) getContext();
		int winnerId = 0;
		int looserId = 0;
		for(int i = 1; i<pirates.size();i++){
			if(pirates.get(winnerId).life<pirates.get(i).life) winnerId = i;
			else if(pirates.get(looserId).life>pirates.get(i).life) looserId = i;
		}
		Pirate winner = pirates.get(winnerId);
		Pirate looser = pirates.get(looserId);
		final LinearLayout ll = new LinearLayout(main);
		ll.setGravity(Gravity.CENTER);
		TextView tv = new TextView(main);
		tv.setText("Winner : " + winner.name);
		tv.setWidth(100);
		ll.addView(tv);
		tv = new TextView(main);
		tv.setText("Looser : " + looser.name);
		tv.setWidth(100);
		ll.addView(tv);
		tv = new TextView(main);
		tv.setText("Time : " + time/1000 + "s");
		tv.setWidth(100);
		ll.addView(tv);
		tv = new TextView(main);
		long score = winner.life*10000 + 100000 - time;
		tv.setText("Score : " + score/1000);
		tv.setWidth(100);
		ll.addView(tv);
		StringBuilder scores = new StringBuilder();
		try{
			FileInputStream fis = main.openFileInput("score");
			Scanner s = new Scanner(fis);
			while(s.hasNextLine())
				scores.append(s.nextLine()+"\n");
			fis.close();
		}catch(IOException e){}
		scores.append(winner.name + " " + looser.name + " " + time/1000 + " " + score/1000);
		try{
			FileOutputStream fos = main.openFileOutput("score", 2);
			fos.write(scores.toString().getBytes());
			fos.close();
		}catch(IOException e){}
		main.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				main.setContentView(ll);
			}
		});
		workerThread.interrupt();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		workerThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		workerThread.interrupt();
	}

	private void drawMap(Canvas canvas){
		for(int i = 0; i<bg.obstacles.size();i++){
			Paint p = new Paint();
			p.setColor(getContext().getResources().getColor(R.color.Black));
			p.setStrokeWidth(2);
			p.setStyle(Style.STROKE);
			//			canvas.drawRect(bg.obstacles.get(i), p);
			canvas.drawBitmap(bg.texture, null, bg.obstacles.get(i), p);
		}
	}
	private void drawPirate(Canvas canvas){
		for(int i = 0; i<2; i++){
			Pirate pirate = bg.arrayPirates.get(i);
			Paint p = new Paint();
			p.setColor(getContext().getResources().getColor(R.color.green));
			canvas.drawBitmap(pirate.texture,
					(float)pirate.coordinate.x-(pirate.texture.getWidth()/2),
					(float)pirate.coordinate.y-(pirate.texture.getHeight()/2), null);
			canvas.drawRect(pirate.getPirateBuffer(), p);
			if(pirate.name.contains("1")){
				p.setColor(getContext().getResources().getColor(R.color.red));
			}else{
				p.setColor(getContext().getResources().getColor(R.color.blue));
			}
//			canvas.drawRect(pirate.getPiratePadBuffer(), p);
		}
	}
	private void drawBonus(Canvas canvas){
		Paint p = new Paint();
		p.setColor(getContext().getResources().getColor(R.color.grey));
		for(int i=0; i<bg.arrayBonus.size();i++){
			if(bg.arrayBonus.get(i).visibility){
				canvas.drawBitmap(bg.arrayBonus.get(i).texture,
				(float)bg.arrayBonus.get(i).coordinate.x-(bg.arrayBonus.get(i).texture.getWidth()/2),
				(float)bg.arrayBonus.get(i).coordinate.y-(bg.arrayBonus.get(i).texture.getHeight()/2), null);
				canvas.drawRect(bg.arrayBonus.get(i).getBuffer(), p);
				
			}
		}
	}
}
