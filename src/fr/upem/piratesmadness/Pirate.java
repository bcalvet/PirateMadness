package fr.upem.piratesmadness;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

public class Pirate {
	Point coordinate;
	int life;
	String name;
	Bitmap texture;
	float speed;
	float speedAcceleration;
	Direction gravity;
	boolean noGravity;
	Direction direction;
	final Rect padBuffer;
	Activity ga;
	
	public Pirate(Point initialCoordinate, Activity activity, int id, Bitmap face) {
		int width = activity.getIntent().getExtras().getInt("width");
		int height = activity.getIntent().getExtras().getInt("height");
		this.texture = face;
		this.ga = activity;
		coordinate = initialCoordinate;
		final int middleX;
		final int middleY;
		if(width<height){
			middleX = width;
			middleY = height/2;
		}else{
			middleX = width/2;
			middleY = height;
		}
		this.padBuffer = new Rect(0, 0, middleX, middleY);
		life = 3;
		speed=1;
		speedAcceleration=0;
	}
	
	public Rect getPiratePadBuffer(){
		switch(ga.getIntent().getExtras().getInt("mode")){
			case 2 : return this.padBuffer;
			default : return new Rect(coordinate.x+50, coordinate.y-50, coordinate.x-50, coordinate.y+50);
		}
	}

	public Rect getPirateBuffer() {
		Rect area = new Rect(coordinate.x+(texture.getWidth()/2),coordinate.y-(texture.getHeight()/2), coordinate.x-(texture.getWidth()/2), coordinate.y+(texture.getHeight()/2));
		return area;
	}
}
