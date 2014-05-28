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
	Rect origin;
	int actualy;
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
		actualy=-1;
		//At the beginning you can't
		twiceJump=10;
		twiceSpeedAcceleration=false;
	}

	public boolean isActually(Rect rec){
//		int plop = -1;
//		boolean result=false;
//		float val = Math.abs(speedAcceleration);
		switch (gravity) {
		case NORTH:
//			plop = rec.bottom;
//			result= ImpactController.isInThisInterval(actualy-(speed+val), rec.bottom, actualy+(speed+val));
//			break;
			return rec.bottom==actualy;
		case SOUTH:
//			plop = rec.top;
//			result= ImpactController.isInThisInterval(actualy-(speed+val), rec.top, actualy+(speed+val));
//			break;
			return rec.top == actualy;
		case EAST:
//			plop = rec.left;
//			result= ImpactController.isInThisInterval(actualy-(speed+val), rec.left, actualy+(speed+val));
//			break;
			return rec.left==actualy;
		case WEST:
//			plop = rec.right;
//			result= ImpactController.isInThisInterval(actualy-(speed+val), rec.right, actualy+(speed+val));
//			break;
			return  rec.right==actualy;
		}
//		Log.d("Pirate", "gravity : " + gravity + " actually " +actualy + " compared to " + plop+"; result : "+result);
//		return result;
//		return rec==origin;
		return false;
	}
	
	public void setActually(Rect rec){
//		Log.d("PiratesMadness","old actualy : "+actualy+"; gravity : "+gravity);
		switch (gravity) {
		case NORTH:
			actualy = rec.bottom;
			break;
		case SOUTH:
			actualy = rec.top;
			break;
		case EAST:
			actualy = rec.left;
			break;
		case WEST:
			actualy = rec.right;
			break;
		}
//		Log.d("PiratesMadness","rect : "+rec.flattenToString()+"; new actually : "+actualy);
//		this.origin = rec;
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
