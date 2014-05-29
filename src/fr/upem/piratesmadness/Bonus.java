package fr.upem.piratesmadness;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Bitmap.CompressFormat;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Bonus implements Parcelable{

	Point coordinate;
	Direction gravity;
	Bitmap texture;
	final int type;
	boolean visibility;
	boolean noGravity;
	
	public Bonus(Parcel in){
		gravity = Direction.valueOf(in.readString());
		int[] idata = new int[3];
		in.readIntArray(idata);
		coordinate = new Point(idata[0], idata[1]);
		type = idata[2];
		boolean[] bdata = new boolean[2];
		in.readBooleanArray(bdata);
		noGravity = bdata[0];
		visibility = bdata[1];
		byte[] array = in.createByteArray();
		this.texture = BitmapFactory.decodeByteArray(array, 0, array.length);
	}
	
    public static final Parcelable.Creator<Bonus> CREATOR = new Parcelable.Creator<Bonus>() {
        public Bonus createFromParcel(Parcel in) {
            return new Bonus(in); 
        }

        public Bonus[] newArray(int size) {
            return new Bonus[size];
        }
    };
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(gravity.name());
		dest.writeIntArray(new int[]{
				coordinate.x, coordinate.y, type
		});
		dest.writeBooleanArray(new boolean[]{
				noGravity, visibility
		});
		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		texture.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
		dest.writeByteArray(blob.toByteArray());
	}
	
	public Bonus(int t) {
		type = t;
		coordinate = new Point(0,0);
	}
	
	static boolean generate(){
		Random random = new Random();
		if(random.nextInt((int)System.currentTimeMillis())%500==0){
			return true;
		}
		return false;
	}
	
	static Bonus bonusFactory(GameArea ga){
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
		Rect buffer = new Rect();
		buffer.left = coordinate.x - (texture.getWidth()/2);
		buffer.top= coordinate.y - (texture.getHeight()/2);
		buffer.right = coordinate.x + (texture.getWidth()/2);
		buffer.bottom = coordinate.y + (texture.getHeight()/2);
		return buffer;
	}
}
