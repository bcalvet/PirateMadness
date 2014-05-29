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
	Rect origin;
	int currently;
	Activity ga;
	//need this variable volatile ?
	volatile int twiceJump;
	volatile boolean twiceSpeedAcceleration;


	public Pirate(Point initialCoordinate, Activity activity, int id, Bitmap face, int number) {
		int width = activity.getIntent().getExtras().getInt("width");
		int height = activity.getIntent().getExtras().getInt("height");
		this.texture = face;
		this.ga = activity;
		coordinate = initialCoordinate;
		if(number==1){
			this.padBuffer = new Rect(0, 0, (width/2), height);
		}else{
			this.padBuffer = new Rect((width/2), 0, width, height);
		}
		life = 3;
		speed=3;
		speedAcceleration=1;
		noGravity=true;
		name=String.valueOf(number);
		currently=-1;
		//At the beginning you can't
		twiceJump=10;
		twiceSpeedAcceleration=false;
	}

	public boolean isCurrently(Rect rec){
		switch (gravity) {
		case NORTH:
			return rec.bottom==currently;
		case SOUTH:
			return rec.top == currently;
		case EAST:
			return rec.left==currently;
		case WEST:
			return  rec.right==currently;
		}
		return false;
	}

	public Rect getPiratePadBuffer(){
		switch(ga.getIntent().getExtras().getInt("mode")){
		case 1 : return this.padBuffer;
		default : return new Rect(coordinate.x+50, coordinate.y-50, coordinate.x-50, coordinate.y+50);
		}
	}

	public Rect getPirateBuffer() {
		Rect pirateBuffer = new Rect(0,0,0,0);
		pirateBuffer.left = coordinate.x - (texture.getWidth()/3);
		pirateBuffer.top= coordinate.y - (texture.getHeight()/3);
		pirateBuffer.right = coordinate.x + (texture.getWidth()/3);
		pirateBuffer.bottom = coordinate.y + (texture.getHeight()/3);
		return pirateBuffer;
	}

	@Override
	public String toString() {
		return "Pirate "+name+"; coordinate(x,y) : ("+coordinate.x+","+coordinate.y+"); texture (height & width) : "+texture.getHeight()+";"+texture.getWidth()+"; gravity : "+noGravity+"; gravity sens : "+gravity+"; direction : "+direction+ "speed : "+speed+"; speedAcceleration : "+speedAcceleration+"; padBuffer : "+padBuffer.flattenToString();
	}

	//	public static ArrayList<Pirate> createPirates(ArrayList<Point> arrayPoints, MainActivity activity, float width, float height) {
	//		ArrayList<Pirate> arrayPirates = new ArrayList<Pirate>();
	//		Bundle extras = activity.getIntent().getExtras();
	//		for(int i=0; i<arrayPoints.size(); i++){
	//			arrayPirates.add(new Pirate(arrayPoints.get(i), activity, 0, 
	//					BattleGround.rescaledBitmap(activity,
	//							width,
	//							height,
	//							extras.getInt("pirate"+i+"_drawable")
	//					)));
	//		}
	//		return arrayPirates;
	//	}
}
