package fr.upem.piratesmadness;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentMain extends Fragment{
	private boolean sound=true;

	public FragmentMain() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Bundle extras = this.getActivity().getIntent().getExtras();
		if(extras==null) extras = new Bundle();
		//		ImageButton bSound = (ImageButton) this.getActivity().findViewById(R.id.main_menu_sound);
		//		Log.d("PiratesMadness", "button bSound : "+bSound.getId());
		//		if(savedInstanceState!=null && savedInstanceState.getBowolean("sound"))bSound.setImageResource(R.drawable.sound_on);
		//		else bSound.setImageResource(R.drawable.sound_off);
		//		media = MediaPlayer.create(this, R.raw.neantitude_robin);
		//		media.start();
		//		media.setLooping(true);
		//		Log.d("PiratesMadness", "vue main");
		//		for(String k : extras.keySet()){
		//			System.out.println("Key : "+k+"; value : "+extras.getString(k));
		//		}
		View view = inflater.inflate(R.layout.fragment_main, null);
		Log.d("PiratesMadness", "view inflated : "+view);
		setButtonOnListener(extras, view);
		return view;
	}

	private void setButtonOnListener(final Bundle savedInstanceState, View v){
		final Activity main = this.getActivity();
		final FragmentManager fm = main.getFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();

		/*
		 * When a button is clicked, you should set enable to false.
		 * Otherwise an error could be throw if user click on an other button during the loading
		 */
		final Button bScore = (Button) v.findViewById(R.id.main_menu_score_board);
		final Button bPlay = (Button) v.findViewById(R.id.main_menu_play);
		final Button bSettings = (Button) v.findViewById(R.id.main_menu_settings);

		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				bScore.setEnabled(false);
				bSettings.setEnabled(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						//debug
						Intent b = getActivity().getIntent();
						b.putExtra("mode", 1);
						b.putExtra("pirate1_drawable", R.drawable.pirate1);
						b.putExtra("pirate2_drawable", R.drawable.pirate2);
						b.putExtra("width", v.getWidth());
						b.putExtra("height", v.getHeight());
						b.putExtra("file_map", "1");
						//end debug
						if(BattleGround.landScape((MainActivity)getActivity(), "1")){
							b.putExtra("reload", true);
							main.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									((MainActivity)getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
								}
							});
						}else{
							b.putExtra("reload", true);
							main.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									((MainActivity)getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
								}
							});
						}
					}
				}).start();
				//				final BattleGroundInitializer bgi = new BattleGroundInitializer((MainActivity)getActivity());
				TextView tv = new TextView(main);
				tv.setWidth(1000);
				tv.setHeight(1000);
				tv.setText("WAITING");
				tv.setTextSize(20);
				tv.setGravity(Gravity.CENTER);
				tv.setBackgroundColor(main.getResources().getColor(R.color.white));
				main.setContentView(tv);
			}
		});
		bSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bScore.setEnabled(false);
				bPlay.setEnabled(false);
				Toast.makeText(getActivity(), "button settings", Toast.LENGTH_SHORT).show();
				ft.replace(android.R.id.content, new FragmentSettings());
				ft.addToBackStack(null);
				ft.commit();  
			}
		});
		bScore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				bSettings.setEnabled(false);
				bPlay.setEnabled(false);
				Toast.makeText(getActivity(), "button score", Toast.LENGTH_SHORT).show();
				ft.replace(android.R.id.content, new FragmentScoreBoard());
				ft.addToBackStack(null);
				ft.commit();  
			}
		});
		final ImageButton bSound = (ImageButton) v.findViewById(R.id.main_menu_sound);
		bSound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "button sound", Toast.LENGTH_SHORT).show();
				sound = !sound;
				if(sound)bSound.setImageResource(R.drawable.sound_on);
				else bSound.setImageResource(R.drawable.sound_off);
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		setButtonOnListener(getActivity().getIntent().getExtras(), this.getView());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putBoolean("sound", sound);
	}

}
