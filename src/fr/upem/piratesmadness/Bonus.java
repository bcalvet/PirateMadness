package fr.upem.piratesmadness;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

public class Bonus {

	Point coordinate;
	Direction gravity;
	Rect buffer;
	Bitmap texture;
	final int type;
	boolean visibility;
	boolean noGravity;
	
	public Bonus(int t) {
		// TODO Auto-generated constructor stub
		type = t;
		coordinate = new Point(0,0);
		buffer = new Rect();
	}
	
	static boolean generate(){
		Random random = new Random();
		if(random.nextInt((int)System.currentTimeMillis())%100==0){
			return true;
		}
		return false;
	}
	
	static Bonus generator(GameArea ga){
		//Storage in hardware of the bonus list possibilities
		int possibilities = 3;
		MainActivity activity = (MainActivity)ga.getContext();
		//1 - speed
		//2 - life
		//3 - special attack
		Random random = new Random();
		Bonus b = new Bonus(random.nextInt((int)System.currentTimeMillis())%possibilities);
		
		float newWidth = (float)activity.getIntent().getExtras().getInt("width") / (float)(ga.bg.width);;
		float newHeight = (float)activity.getIntent().getExtras().getInt("height") / (float)(ga.bg.height);
		
		switch (b.type) {
		case 0:
			b.texture = BattleGround.rescaledBitmap(activity,
					newWidth,
					newHeight,
					R.drawable.life
					);
			break;
		case 1:
			b.texture = BattleGround.rescaledBitmap(activity,
					newWidth,
					newHeight,
					R.drawable.rhum
					);
			break;
		case 2:
			b.texture = BattleGround.rescaledBitmap(activity,
					newWidth,
					newHeight,
					R.drawable.speed
					);
			break;
		}
		ArrayList<Rect> obstacles = ga.bg.obstacles;
		b.coordinate.x=random.nextInt((int)System.currentTimeMillis())%ga.bg.width;
		b.coordinate.y=random.nextInt((int)System.currentTimeMillis())%ga.bg.height;
		int size = obstacles.size();
		for(int i=0; i<size; i++){
			if(Rect.intersects(b.getBuffer(), obstacles.get(i))){
				b.coordinate.x=random.nextInt((int)System.currentTimeMillis())%ga.getWidth();
				b.coordinate.y=random.nextInt((int)System.currentTimeMillis())%ga.getHeight();
				Log.d("PiratesMadness","intersection during creation, move : "+b.coordinate.x+","+b.coordinate.y);
				i=0;
			}
		}
		b.gravity = Direction.random(random);
		b.noGravity=true;
		b.visibility=true;
		return b;
	}
	
	public Rect getBuffer(){
		buffer.left = coordinate.x - (texture.getWidth()/2);
		buffer.top= coordinate.y - (texture.getHeight()/2);
		buffer.right = coordinate.x + (texture.getWidth()/2);
		buffer.bottom = coordinate.y + (texture.getHeight()/2);
		return buffer;
	}
	
	void setEffect(final Pirate p){
		visibility=false;
		switch(type){
		case 0:
			p.life++;
			p.ga.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(p.ga, "Pirate "+p.name+" win a life", Toast.LENGTH_SHORT).show();
				}
			});
			return;
		case 1:
			p.direction = p.direction.oppositeDirection();
			return;
		case 2:
			p.speed+=p.speed;
			return;
		}
	}
	
	boolean consumeEffect(Pirate p){
		if(Rect.intersects(p.getPirateBuffer(), buffer)){
			setEffect(p);
			return true;
		}
		return false;
	}

}
