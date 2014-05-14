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
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
		Button bPlay = (Button) v.findViewById(R.id.main_menu_play);
		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final BattleGroundInitializer bgi = new BattleGroundInitializer((MainActivity)getActivity());
				//debug
				Intent b = getActivity().getIntent();
				b.putExtra("mode", 1);
				b.putExtra("pirate1_drawable", R.drawable.pirate1);
				b.putExtra("pirate2_drawable", R.drawable.pirate2);
				b.putExtra("width", v.getWidth());
				b.putExtra("height", v.getHeight());
				//end debug
				getActivity().setContentView(R.layout.fragment_initializer);
				bgi.execute("1");
			}
		});
		Button bSettings = (Button) v.findViewById(R.id.main_menu_settings);
		bSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "button settings", Toast.LENGTH_SHORT).show();
				ft.replace(android.R.id.content, new FragmentSettings());
				ft.addToBackStack(null);
				ft.commit();  
			}
		});
		Button bScore = (Button) v.findViewById(R.id.main_menu_score_board);
		bScore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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
				//Normalement l'enregistrement des données dans le bundle se fait dans une autre méthode
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
