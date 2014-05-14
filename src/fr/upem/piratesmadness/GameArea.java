package fr.upem.piratesmadness;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {
	BattleGround bg;
	
	public GameArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("PiratesMadness","GameArea");
		getHolder().addCallback(this);
//		if(bg==null){
//			Log.e("pirate", "damned!");
//			((Activity)getContext()).finish();
//		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("PiratesMadness","SurfaceCreated");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("PiratesMadness","GameArea battle : "+bg);
		Canvas canvas = holder.lockCanvas();
		drawMap(canvas);
		holder.unlockCanvasAndPost(canvas);
		Log.d("PiratesMadness","SurfaceChanged");
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
//			canvas.drawBitmap(bg.texture, null, bg.obstacles.get(i), p);
		}
	}
	private void drawPirate(){
		
	}

}
