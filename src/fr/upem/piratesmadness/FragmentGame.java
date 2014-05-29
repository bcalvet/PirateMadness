package fr.upem.piratesmadness;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentGame extends Fragment{
	public FragmentGame() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		MainActivity activity = ((MainActivity)getActivity());
		if(getActivity().getIntent().getExtras().getBoolean("sound")){
			activity.media = MediaPlayer.create(getActivity(), R.raw.he_s_a_pirate);
			activity.media.setLooping(true);
			activity.media.start();
		}
		View view = inflater.inflate(R.layout.fragment_game, null);
		return view;
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try{
			if(((MainActivity)getActivity()).media!=null && ((MainActivity)getActivity()).media.isPlaying()){
				((MainActivity)getActivity()).media.pause();
				((MainActivity)getActivity()).media.release();
			}
		}catch(IllegalStateException ise){
			//TODO NOTHING : BUG WITH isPlaying
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getActivity().getIntent().getExtras()!=null && getActivity().getIntent().getExtras().getBoolean("sound")){
			((MainActivity)getActivity()).media = MediaPlayer.create(getActivity(), R.raw.he_s_a_pirate);
			((MainActivity)getActivity()).media.start();
		}
	}
}
