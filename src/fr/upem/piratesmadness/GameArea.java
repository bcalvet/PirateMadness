package fr.upem.piratesmadness;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
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
		bg = BattleGround.initGame(main);

		//Initialization of the pirates must be done before this Thread due to the destruction of this thread

		//Initialization of the IAController too
		final IAController ia = new IAController();
		workerThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Infinity cycle to move and update information
				for(;;){
					ia.update(bg);;
				}
			}
		});
		workerThread.setDaemon(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
//		Log.d("PiratesMadness","SurfaceCreated");
		
		workerThread.start();
		Canvas canvas = holder.lockCanvas();
		drawMap(canvas);
		drawPirate(canvas);
		holder.unlockCanvasAndPost(canvas);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
//		Log.d("PiratesMadness","SurfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	private void drawMap(Canvas canvas){
		canvas.drawARGB(0, 0, 0, 0);
		for(int i = 0; i<bg.obstacles.size();i++){
			Paint p = new Paint();
			p.setColor(getContext().getResources().getColor(R.color.Black));
			canvas.drawRect(bg.obstacles.get(i), p);
			canvas.drawBitmap(bg.texture, null, bg.obstacles.get(i), p);
		}
	}
	private void drawPirate(Canvas canvas){
		for(int i = 0; i<2; i++){
			Pirate pirate = bg.arrayPirates.get(i);
			canvas.drawBitmap(pirate.texture, (float)pirate.coordinate.x, (float)pirate.coordinate.y, null);
		}
	}
}
