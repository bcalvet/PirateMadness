package fr.upem.piratesmadness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

			new_width = (float)activity.getIntent().getExtras().getInt("width") / (float)(bg.width);
			new_height = (float)activity.getIntent().getExtras().getInt("height") / (float)(bg.height);

			bg.texture = rescaledBitmap(activity, new_width, new_height, R.drawable.wall);

			//Utiliser la factory pour créer une ArrayList<Pirate>
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
											),
											1
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
											),
											2
							)
					);
			bg.arrayPirates = pirates;

			//Translating map to background
			for (int i = 0; i < bg.width; i++) {
				int size = map.get(i, new ArrayList<Integer>()).size();
				for (int j = 0; j < size; j++) {
					int y = i*bg.texture.getHeight();
					int x = map.get(i).get(j)* bg.texture.getWidth();
					bg.obstacles.add(
							new Rect(x , y ,x+bg.texture.getWidth(),y+bg.texture.getHeight()));
				}
			}

			//Refactoring background
//			int checked = 1;
//			while(checked!=0){
//				checked = 0;
//				for(Rect r1 : bg.obstacles){
//					for(Rect r2 : bg.obstacles){
//						if(r1!=r2 && r1.intersect(r2) && (r1.centerX()==r2.centerX()||r1.centerY()==r2.centerY())){
//							bg.obstacles.remove(r1);
//							bg.obstacles.remove(r2);
//							int ymin = Math.min(r1.centerY()-r1.height()/2, r2.centerY()-r1.height()/2);
//							int ymax = Math.max(r1.centerY()+r1.height()/2, r2.centerY()+r1.height()/2);
//							int xmin = Math.min(r1.centerX()-r1.width()/2, r2.centerX()-r1.width()/2);
//							int xmax = Math.min(r1.centerX()+r1.width()/2, r2.centerX()+r1.width()/2);
//							bg.obstacles.add(new Rect(ymin,xmin,ymax,xmax));
//							checked++;
//							break;
//						}
//					}
//				}
//			}

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

	static Bitmap rescaledBitmap(MainActivity ga, float new_width, float new_height, int drawable){
		Bitmap basic = BitmapFactory.decodeResource(ga.getResources(),
				drawable);
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
