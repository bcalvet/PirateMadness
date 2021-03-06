package fr.upem.piratesmadness;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentMain extends Fragment{
	//	private boolean sound=true;

	public FragmentMain() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		Bundle extras = this.getActivity().getIntent().getExtras();
		if(extras==null) extras = new Bundle();
		View view = inflater.inflate(R.layout.fragment_main, null);
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
		final View main_view = v;
		bPlay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				bScore.setEnabled(false);
				bSettings.setEnabled(false);
				final Intent b = getActivity().getIntent();
				b.putExtra("width", main_view.getWidth());
				b.putExtra("height", main_view.getHeight());
				MainActivity activity = (MainActivity)getActivity();
				if(activity.media!=null && activity.media.isPlaying()){
					activity.media.pause();
					activity.media.stop();
					activity.media.release();
				}
				ft.replace(android.R.id.content, new FragmentGame());
				ft.addToBackStack(null);
				ft.commit();
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
		//		final ImageButton bSound = (ImageButton) v.findViewById(R.id.main_menu_sound);
		//		bSound.setOnClickListener(new OnClickListener() {
		//			@Override
		//			public void onClick(View v) {
		//				Toast.makeText(getActivity(), "button sound", Toast.LENGTH_SHORT).show();
		//				sound = !sound;
		//				if(sound)bSound.setImageResource(R.drawable.sound_on);
		//				else bSound.setImageResource(R.drawable.sound_off);
		//			}
		//		});
	}

	//	@Override
	//	public void onResume() {
	//		super.onResume();
	//		setButtonOnListener(getActivity().getIntent().getExtras(), this.getView());
	//	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//		outState.putBoolean("sound", sound);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MainActivity activity = ((MainActivity)getActivity());
		try{
			if(getActivity().getIntent().getExtras()!=null && getActivity().getIntent().getExtras().getBoolean("sound") && activity.media!=null){
				activity.media = MediaPlayer.create(getActivity(), R.raw.the_medallion_calls);
				activity.media.start();
				activity.media.setLooping(true);
			}
		}catch(IllegalStateException ise){
			//TODO Nothing : bug with isPlaying()
		}
	}
}
