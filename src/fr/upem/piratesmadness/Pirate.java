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
	Rect pirateBuffer = new Rect(0,0,0,0);
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
		speed=3;
		speedAcceleration=1;
		noGravity=true;
	}
	
	public Rect getPiratePadBuffer(){
		switch(ga.getIntent().getExtras().getInt("mode")){
			case 2 : return this.padBuffer;
			default : return new Rect(coordinate.x+50, coordinate.y-50, coordinate.x-50, coordinate.y+50);
		}
	}

	public Rect getPirateBuffer() {
		pirateBuffer.left = coordinate.x-(texture.getWidth()/2);
		pirateBuffer.top= coordinate.y - (texture.getHeight()/2);
		pirateBuffer.right = coordinate.x + (texture.getWidth()/2);
		pirateBuffer.bottom = coordinate.y + (texture.getHeight()/2);
		return pirateBuffer;
	}
	
	@Override
	public String toString() {
		return "Pirate "+name+"; coordinate(x,y) : ("+coordinate.x+","+coordinate.y+"); texture (height & width) : "+texture.getHeight()+";"+texture.getWidth()+"; gravity : "+noGravity+"; gravity sens : "+gravity+"; direction : "+direction+ "speed : "+speed+"; speedAcceleration : "+speedAcceleration+"; padBuffer : "+padBuffer.flattenToString();
	}
}
