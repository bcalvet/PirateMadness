package fr.upem.piratesmadness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

public class BattleGround {
	ArrayList<Rect> obstacles;
	Bitmap texture;
	MediaPlayer sound;
	//Useful : indicates if the map is more higher than wider
	boolean isLandscape;
	int width;
	int height;
	int difficulty;
	ArrayList<Pirate> arrayPirates;


	static BattleGround initGame(MainActivity activity) {
		try {			
			BattleGround bg = new BattleGround();
			bg.obstacles = new ArrayList<Rect>();
			Bundle extras = activity.getIntent().getExtras();
			bg.difficulty = extras.getInt("mode");
			int height = 0;
			SparseArray<ArrayList<Integer>> map = new SparseArray<ArrayList<Integer>>();
			ArrayList<Pirate> pirates = new ArrayList<Pirate>();
			Point pirate1 = new Point();
			Point pirate2 = new Point();
			float new_width;
			float new_height;

			Scanner s = new Scanner(activity.getAssets().open(extras.getString("file_map")));
			boolean firstCircle = true;
			String line;
			while (s.hasNextLine()) {
				line = s.nextLine();
				int x = 0;
				for (char c : line.toCharArray()) { //while pour perf
					ArrayList<Integer> current = map.get(height,
							new ArrayList<Integer>());
					if (c == 'x'){
						current.add(x);
					}else if(c == '1' || c == '2'){ // A modifier pour plus de joueurs
						if(Integer.parseInt(Character.toString(c))==1)
							pirate1 = new Point(x, height);
						else
							pirate2 = new Point(x, height);
					}
					map.put(height, current);
					x++;
				}
				height++;
				if (firstCircle)
					bg.width = x;
				firstCircle = false;
			}
			bg.height = height;
			if (bg.width == 0 || height == 0)
				throw new IllegalStateException();

			bg.isLandscape = bg.width>height;
			if(bg.isLandscape = bg.width>height){
				activity.getIntent().putExtra("landscape", true);
				//				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}else{
				activity.getIntent().putExtra("landscape", false);
				//				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}

			new_width = (float)activity.getIntent().getExtras().getInt("width") / (float)(bg.width);
			new_height = (float)activity.getIntent().getExtras().getInt("height") / (float)(bg.height);

			bg.texture = rescaledBitmap(activity, new_width, new_height, R.drawable.wall);

			//			activity.getResources().getDrawable(extras.getInt("pirate1_drawable"));
			pirates.add(
					new Pirate(
							new Point((int)(pirate1.x*new_width),
									(int)(pirate1.y*new_height)),
									activity,
									0,
									rescaledBitmap(activity,
											new_width,
											new_height,
											extras.getInt("pirate1_drawable")
											)
							)
					);
			pirates.add(
					new Pirate(
							new Point((int)(pirate2.x*new_width),
									(int)(pirate2.y*new_height)),
									activity,
									0,
									rescaledBitmap(activity,
											new_width,
											new_height,
											extras.getInt("pirate2_drawable")
											)
							)
					);
			bg.arrayPirates = pirates;

//			for(int i = 0 ; i<((bg.isLandscape)?bg.height:bg.width); i++){
//				int size = map.get(i, new ArrayList<Integer>()).size();
//				for(int j = 0 ; j<size;j++){
//					int y = i * (int)((bg.isLandscape)?new_height:new_width);
//					int x = map.get(i).get(j) * (int)((bg.isLandscape)?new_width:new_height);
//					bg.obstacles.add(
//							new Rect(
//									x,
//									y,
//									x+(int)((bg.isLandscape)?new_height:new_width),
//									y+(int)((bg.isLandscape)?new_width:new_height)
//									)
//							);
//				}
//			}
			
			for (int i = 0; i < ((bg.isLandscape)?bg.width:bg.height); i++) {
				int size = map.get(i, new ArrayList<Integer>()).size();
				for (int j = 0; j < size; j++) {
					int y = i*bg.texture.getHeight();
					int x = map.get(i).get(j)* bg.texture.getWidth();
					bg.obstacles.add(
							new Rect(x , y ,x+bg.texture.getWidth(),y+bg.texture.getHeight()));
				}
			}

			return bg;
		} catch (IOException ise) {
			Log.e("pirate", "Can't parse level file!");
			ise.printStackTrace();
			System.exit(-1);
		}
		Log.e("pirate", "Can't create BattleGround! Abord activity!");
		System.exit(-1);
		return null;
	}

	private static Bitmap rescaledBitmap(MainActivity ga, float new_width, float new_height, int drawable){
		Bitmap basic = BitmapFactory.decodeResource(ga.getResources(),
				drawable);
		Log.d("PiratesMadness","NEW width : "+new_width+"height : "+new_height);
		Log.d("PiratesMadness","BASIC width : "+basic.getWidth()+"height : "+basic.getHeight());
		Matrix matrix = new Matrix();
		matrix.postScale(new_width / basic.getWidth(),
				new_height / basic.getHeight());
		return Bitmap.createBitmap(basic, 0, 0, basic.getWidth(),
				basic.getHeight(), matrix, true);
	}

	public static boolean landScape(MainActivity activity, String file){
		Scanner s;
		int width=0;
		int height=0;
		try {
			s = new Scanner(activity.getAssets().open(file));
			boolean firstCircle = true;
			String line;
			while (s.hasNextLine()) {
				line = s.nextLine();
				if(firstCircle){
					int size_line = line.length();
					int x = 0;
					for (x=0; x<size_line; x++) {
						x++;
					}
					width = x;
				}
				height++;
				firstCircle = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return width>height;
	}
}
