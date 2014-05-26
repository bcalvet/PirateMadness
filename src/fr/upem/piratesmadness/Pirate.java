package fr.upem.piratesmadness;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

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
	int actualy;



	public Pirate(Point initialCoordinate, Activity activity, int id, Bitmap face, int number) {
		int width = activity.getIntent().getExtras().getInt("width");
		int height = activity.getIntent().getExtras().getInt("height");
		this.texture = face;
		this.ga = activity;
		coordinate = initialCoordinate;
		//		final int middleX;
		//		final int middleY;
		//		if(width<height){
		//			middleX = width;
		//			middleY = height/2;
		//		}else{
		//			middleX = width/2;
		//			middleY = height;
		//		}

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
		actualy=-1;
	}

	public boolean isActually(Rect rec){
		switch (gravity) {
		case NORTH:
			return rec.bottom==actualy;
		case SOUTH:
			return rec.top==actualy;
		case EAST:
			return rec.left==actualy;
		case WEST:
			return rec.right==actualy;
		}
		return false;
	}
	
	public void setActually(Rect rec){
		switch (gravity) {
		case NORTH:
			actualy = rec.bottom;
		case SOUTH:
			actualy = rec.top;
		case EAST:
			actualy = rec.left;
		case WEST:
			actualy = rec.right;
		}
	}

	public Rect getPiratePadBuffer(){
		switch(ga.getIntent().getExtras().getInt("mode")){
		case 1 : return this.padBuffer;
		default : return new Rect(coordinate.x+50, coordinate.y-50, coordinate.x-50, coordinate.y+50);
		}
	}

	public Rect getPirateBuffer() {
		pirateBuffer.left = coordinate.x-(texture.getWidth()/3);
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
	//		// TODO Auto-generated method stub
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
