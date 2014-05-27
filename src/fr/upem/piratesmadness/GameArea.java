package fr.upem.piratesmadness;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameArea extends SurfaceView implements SurfaceHolder.Callback {
	BattleGround bg;
	Thread workerThread;

	public GameArea(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		MainActivity main = (MainActivity) getContext();
		final GameArea ga = this;
		bg = BattleGround.initGame(main);


		//Initialization of the IAController too
		final IAController ia = new IAController();
		final ImpactController impactController = new ImpactController(); 

		OnTouchListener otl = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
//					Log.d("PiratesMadness","évènement dans le onTouch");
					int id = -1;
					if(bg.arrayPirates.get(0).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY())){
						if(!bg.arrayPirates.get(1).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY()))
							id = 0;
					} else if(bg.arrayPirates.get(1).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY())){
						if(!bg.arrayPirates.get(0).getPiratePadBuffer().contains((int)event.getX(), (int)event.getY()))
							id = 1;
					}
					if(id!=-1){
						ia.startingToJump(bg.arrayPirates.get(id));
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
				for(int i=0; i<bg.arrayPirates.size(); i++){
					Pirate pirate = bg.arrayPirates.get(i);
					if(pirate.gravity==null)
						pirate.gravity=Direction.SOUTH;
					pirate.gravity.randomDirection(pirate);
//					Log.d("PiratesMadness",pirate.toString());
				}
				//Infinity cycle to move and update information
				while(!Thread.currentThread().isInterrupted()){
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
			if(pirate.name.contains("1")){
				p.setColor(getContext().getResources().getColor(R.color.red));
			}else{
				p.setColor(getContext().getResources().getColor(R.color.blue));
			}
			canvas.drawRect(pirate.getPiratePadBuffer(), p);
		}
	}
}
