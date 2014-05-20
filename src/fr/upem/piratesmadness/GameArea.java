package fr.upem.piratesmadness;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {
	BattleGround bg;
	Thread workerThread;
	
	public GameArea(Context context, AttributeSet attrs) {
		super(context, attrs);
//		Log.d("PiratesMadness","GameArea");
		getHolder().addCallback(this);
//		if(bg==null){
//			Log.e("pirate", "damned!");
//			((Activity)getContext()).finish();
//		}
		MainActivity main = (MainActivity) getContext();
		final GameArea ga = this;
		bg = BattleGround.initGame(main);

		//Initialization of the pirates must be done before this Thread due to the destruction of this thread

		//Initialization of the IAController too
		final IAController ia = new IAController();
		final ImpactController impactController = new ImpactController(); 
		workerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Initialization of gravity and direction
//				impactController.update(bg);
				for(int i=0; i<bg.arrayPirates.size(); i++){
					Pirate pirate = bg.arrayPirates.get(i);
					if(pirate.gravity==null)
						pirate.gravity=Direction.SOUTH;
					pirate.gravity.randomDirection(pirate);
					Log.d("PiratesMadness",pirate.toString());
				}
				//Infinity cycle to move and update information
				while(!Thread.interrupted()){
					SurfaceHolder holder = ga.getHolder();
					impactController.update(bg);
					ia.update(bg);
					try{
						Canvas canvas = holder.lockCanvas();
						canvas.drawRGB(255, 255, 255);
						drawMap(canvas);
						drawPirate(canvas);
						holder.unlockCanvasAndPost(canvas);
					}catch(NullPointerException npe){
						//Do Nothing
					}
				}
			}
		});
		workerThread.setDaemon(true);
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
			canvas.drawRect(bg.obstacles.get(i), p);
//			canvas.drawBitmap(bg.texture, null, bg.obstacles.get(i), p);
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
		}
	}
}
