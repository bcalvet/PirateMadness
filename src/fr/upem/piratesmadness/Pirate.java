package fr.upem.piratesmadness;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.os.Parcel;
import android.os.Parcelable;

public class Pirate implements Parcelable{
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
	int mode;
	int id;
	int width;
	int height;
	//need this variable volatile ?
	volatile int twiceJump;
	volatile boolean twiceSpeedAcceleration;


	public Pirate(Point initialCoordinate, Activity activity, int id, Bitmap face, int mode) {
		this.id = id;
		width = activity.getIntent().getExtras().getInt("width");
		height = activity.getIntent().getExtras().getInt("height");
		this.texture = face;
		this.mode = activity.getIntent().getExtras().getInt("mode");
		coordinate = initialCoordinate;
		if(mode==1){
			if(id==1){
				this.padBuffer = new Rect(0, 0, (width/2), height);
			}else{
				this.padBuffer = new Rect((width/2), 0, width, height);
			}
		}else{
			padBuffer = new Rect(coordinate.x+50, coordinate.y-50, coordinate.x-50, coordinate.y+50);
		}
		life = 3;
		speed=3;
		speedAcceleration=1;
		noGravity=true;
		name=activity.getIntent().getExtras().getString("player"+id);
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
		switch(mode){
		case 1 : break;
		default : 
			this.padBuffer.left=coordinate.x-50;
			this.padBuffer.top=coordinate.y-50;
			this.padBuffer.right=coordinate.x+50;
			this.padBuffer.bottom = coordinate.y+50;
		}
		return this.padBuffer;
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
		return "Pirate (id:"+id+") "+name+"; coordinate(x,y) : ("+coordinate.x+","+coordinate.y+"); texture (height & width) : "+texture.getHeight()+";"+texture.getWidth()+"; gravity : "+noGravity+"; gravity sens : "+gravity+"; direction : "+direction+ "speed : "+speed+"; speedAcceleration : "+speedAcceleration+"; padBuffer : "+padBuffer.flattenToString();
	}

	
	public Pirate(Parcel in){
		name=in.readString();
		int[] idata = new int[9];
		in.readIntArray(idata);
		this.id = idata[0];
		int width = idata[1];
		int height = idata[2];
		this.mode = idata[3];
		coordinate = new Point(idata[4], idata[5]);
		if(mode==1){
			this.padBuffer = new Rect(0, 0, (width/2), height);
		}else{
			this.padBuffer = new Rect((width/2), 0, width, height);
		}
		life = idata[6];
		currently=idata[7];
		twiceJump=idata[8];
		float[] fdata = new float[2];
		in.readFloatArray(fdata);
		speed=fdata[0];
		speedAcceleration=fdata[1];
		boolean[] bdata = new boolean[2];
		in.readBooleanArray(bdata);
		noGravity=bdata[0];
		twiceSpeedAcceleration=bdata[1];
		byte[] array = in.createByteArray();
		this.texture = BitmapFactory.decodeByteArray(array, 0, array.length);
	}
	
    public static final Parcelable.Creator<Pirate> CREATOR = new Parcelable.Creator<Pirate>() {
        public Pirate createFromParcel(Parcel in) {
            return new Pirate(in); 
        }

        public Pirate[] newArray(int size) {
            return new Pirate[size];
        }
    };
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeIntArray(new int[]{
				id, width, height, mode, coordinate.x, coordinate.y, life, currently, twiceJump 
		});
		dest.writeFloatArray(new float[]{
				speed, speedAcceleration
		});
		dest.writeBooleanArray(new boolean[]{
				noGravity, twiceSpeedAcceleration
		});
		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		texture.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
		dest.writeByteArray(blob.toByteArray());
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
