package fr.upem.piratesmadness;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

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

	public Runnable init(InputStream file, View v){
		Runnable r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*try {
					BattleGround bg = new BattleGround();
					bg.obstacles = new ArrayList<Rect>();
					bg.easy = isEasy;
					int height = 0;
					SparseArray<ArrayList<Integer>> map = new SparseArray<ArrayList<Integer>>();
					Pirates[] pirates = new Pirates[2];
					GameArea ga =((GameArea)daddy.findViewById(R.id.gameArea));
					Point pirate1 = new Point();
					Point pirate2 = new Point();
					Scanner s = new Scanner(level);
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
							}else if(c == '1' || c == '2'){
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
					bg.map = map;
					bg.height = height;
					if (bg.width == 0 || height == 0)
						throw new IllegalStateException();
					float new_width;
					float new_height;
					if(bg.landscape = bg.width>height)
						daddy.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					else
						daddy.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

					new_width = (float)ga.getWidth() / (float)(bg.width);
					new_height = (float)ga.getHeight() / (float)(height);
					bg.texture = rescaledBitmap(ga, new_width, new_height, R.drawable.wall);
					pirates[0] = new Pirates(new Point((int)(pirate1.x*new_width), (int)(pirate1.y*new_height)), ga, 0, rescaledBitmap(ga, new_width, new_height, DrawablePirate1));
					pirates[1] = new Pirates(new Point((int)(pirate2.x*new_width), (int)(pirate2.y*new_height)), ga, 0, rescaledBitmap(ga, new_width, new_height, DrawablePirate2));
					bg.pirates = pirates;
					return bg;
				} catch (IllegalStateException ise) {
					Log.e("pirate", "Can't parse level file!");
					System.exit(-1);
				}
				Log.e("pirate", "Can't create BattleGround! Abord activity!");
				System.exit(-1);
				return null;*/
			}
		};
		return r;
	}

//	private Bitmap rescaledBitmap(View v,float new_width, float new_height, int drawable){	
//		Bitmap basic = BitmapFactory.decodeResource(v.getResources(),
//				drawable);
//		Matrix matrix = new Matrix();
//		matrix.postScale(new_width / basic.getWidth(),
//				new_height / basic.getHeight());
//		return Bitmap.createBitmap(basic, 0, 0, basic.getWidth(),
//				basic.getHeight(), matrix, true);
//	}	
}
