package fr.upem.piratesmadness;
import java.util.ArrayList;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class GameArea extends SurfaceView implements SurfaceHolder.Callback {
	BattleGround bg;
	
	public GameArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d("PiratesMadness","GameArea");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("PiratesMadness","SurfaceCreated");
		MainActivity activity = (MainActivity) getContext();
		if(activity.getIntent().getExtras().getBoolean("landscale")){
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}else{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("PiratesMadness","SurfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	private void drawMap(Canvas canvas){
//		for (int i = 0; i < ((bg.isLandscape())?bg.getWidth():bg.getHeight()); i++) {
//			int size = bg.getMap().get(i, new ArrayList<Integer>()).size();
//			for (int j = 0; j < size; j++) {
//				int y = i;
//				int x = bg.getMap().get(i).get(j);
//				canvas.drawBitmap(bg.getTexture(), x * bg.getTexture().getWidth(), y * bg.getTexture().getHeight(), null);
//			}
//		}	
	}
	private void drawPirate(){};

}
