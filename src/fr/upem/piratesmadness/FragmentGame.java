package fr.upem.piratesmadness;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

public class FragmentGame extends Fragment{
	BattleGround battle;

	public FragmentGame() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_game, null);
		GameArea ga = (GameArea) view.findViewById(R.id.fr_upem_piratesmadness_fragment_game_surfaceview);
		ga.bg = battle;
		Log.d("PiratesMadness","fragment game ok");
		return view;
	}
}