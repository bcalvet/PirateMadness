package fr.upem.piratesmadness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BattleGroundInitializer extends
AsyncTask<String, String, BattleGround> {
	volatile MainActivity activity;

	public BattleGroundInitializer(MainActivity act) {
		activity = act;
	}


	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 * Params[0] : name of file
	 * Params[1] : drawable pirate1
	 * Params[2] : drawable pirate2
	 */
	@Override
	protected BattleGround doInBackground(String... params) {
		try {
			if(isCancelled()){
				return null;
			}
			publishProgress(activity.getString(R.string.init_progress1));
			BattleGround bg = new BattleGround();
			bg.obstacles = new ArrayList<Rect>();
			bg.difficulty = activity.getIntent().getExtras().getInt("mode");
			int height = 0;
			SparseArray<ArrayList<Integer>> map = new SparseArray<ArrayList<Integer>>();
			ArrayList<Pirate> pirates = new ArrayList<Pirate>();
			Point pirate1 = new Point();
			Point pirate2 = new Point();
			Scanner s = new Scanner(activity.getAssets().open(params[0]));
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
				if(isCancelled()){
					return null;
				}
			}
			publishProgress(activity.getString(R.string.init_progress2));
			bg.height = height;
			if (bg.width == 0 || height == 0)
				throw new IllegalStateException();
			float new_width;
			float new_height;
			if(bg.isLandscape = bg.width>height)
				activity.getIntent().getExtras().putBoolean("landscape", true);
			//				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			else
				activity.getIntent().getExtras().putBoolean("landscape", false);
			//				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			if(isCancelled()){
				return null;
			}
			publishProgress(activity.getString(R.string.init_progress3));
			new_width = (float)activity.getIntent().getExtras().getInt("width") / (float)(bg.width);
			new_height = (float)activity.getIntent().getExtras().getInt("height") / (float)(bg.height);
			bg.texture = rescaledBitmap(activity, new_width, new_height, R.drawable.wall);
			activity.getResources().getDrawable(activity.getIntent().getExtras().getInt("pirate1_drawable"));
			pirates.add(
					new Pirate(
							new Point((int)(pirate1.x*new_width),
									(int)(pirate1.y*new_height)),
									activity,
									0,
									rescaledBitmap(activity,
											new_width,
											new_height,
											activity.getIntent().getExtras().getInt("pirate1_drawable")
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
											activity.getIntent().getExtras().getInt("pirate2_drawable")
											)
							)
					);
			bg.arrayPirates = pirates;
			if(isCancelled()){
				return null;
			}
			publishProgress(activity.getString(R.string.init_progress4));
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

	private Bitmap rescaledBitmap(MainActivity ga, float new_width, float new_height, int drawable){
		Bitmap basic = BitmapFactory.decodeResource(ga.getResources(),
				drawable);
		Matrix matrix = new Matrix();
		matrix.postScale(new_width / basic.getWidth(),
				new_height / basic.getHeight());
		return Bitmap.createBitmap(basic, 0, 0, basic.getWidth(),
				basic.getHeight(), matrix, true);
	}

	@Override
	protected void onProgressUpdate(String... values) {
		TextView tx = (TextView) activity.findViewById(R.id.fr_upem_piratesmadness_BattleGroundInitialize_textview);
		tx.setText(values[values.length-1]);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		final BattleGroundInitializer bgi = this;
		ProgressBar pb = (ProgressBar) activity.findViewById(R.id.fr_upem_piratesmadness_BattleGroundInitialize_progressbar);
		pb.setIndeterminate(true);
		Button exit = (Button) activity.findViewById(R.id.fr_upem_piratesmadness_BattleGroundInitialize_button);
		exit.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				bgi.cancel(true);
				bgi.activity.asyncTask=null;
			}
		});			
	}
	
	@Override
	protected void onPostExecute(final BattleGround result) {
		super.onPostExecute(result);
		Log.d("PiratesMadness", "result postExecute");
		if(activity!=null){
			activity.asyncTask=null;
		}
		if(result==null){
			Log.e("PiratesMadness", "Error for the result : value is null");
			System.exit(-1);
		}
				activity.runOnUiThread(new Runnable(){
					
					public void run() {
						
						FragmentManager fm = activity.getFragmentManager();
						FragmentGame fg = new FragmentGame();
						fg.battle = result;
						FragmentTransaction ft = fm.beginTransaction();
						ft.replace(android.R.id.content, fg);
						ft.addToBackStack(null);
						ft.commit();}
				});
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		onPostExecute(null);
	}

	@Override
	protected void onCancelled(BattleGround result) {
		super.onCancelled(result);
		onPostExecute(result);
	}
}
