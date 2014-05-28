package fr.upem.piratesmadness;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;

public class FragmentSettings extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_settings, null);
		setButtonOnListener(getActivity().getIntent().getExtras(), view);
		LayoutParams params = new LayoutParams(200, LayoutParams.WRAP_CONTENT);
		/*Player 1 panel*/
		LinearLayout player1 = (LinearLayout) view
				.findViewById(R.id.player1_image);
		player1.setBackgroundColor(getResources().getColor(R.color.blue));
		player1.addView(getPreconfiguredImageView(R.drawable.pirate1, params, 1));
		player1.addView(getPreconfiguredImageView(R.drawable.pirate2, params, 1));
		player1.addView(getPreconfiguredImageView(R.drawable.pirate3, params, 1));
		player1.addView(getPreconfiguredImageView(R.drawable.pirate4, params, 1));
		player1.addView(getPreconfiguredImageView(R.drawable.pirate5, params, 1));
		player1.addView(getPreconfiguredImageView(R.drawable.pirate6, params, 1));
		/*Player 2 panel*/
		LinearLayout player2 = (LinearLayout) view
				.findViewById(R.id.player2_image);
		player2.setBackgroundColor(getResources().getColor(R.color.red));
		player2.addView(getPreconfiguredImageView(R.drawable.pirate1, params, 2));
		player2.addView(getPreconfiguredImageView(R.drawable.pirate2, params, 2));
		player2.addView(getPreconfiguredImageView(R.drawable.pirate3, params, 2));
		player2.addView(getPreconfiguredImageView(R.drawable.pirate4, params, 2));
		player2.addView(getPreconfiguredImageView(R.drawable.pirate5, params, 2));
		player2.addView(getPreconfiguredImageView(R.drawable.pirate6, params, 2));
		/*Map panel*/
		LinearLayout map = (LinearLayout) view
				.findViewById(R.id.map_image);
		map.setBackgroundColor(getResources().getColor(R.color.grey));
		map.addView(getPreconfiguredMapImageView(R.drawable.map1, params, 1));
		map.addView(getPreconfiguredMapImageView(R.drawable.map3, params, 3));
		return view;
	}

	private ImageView getPreconfiguredMapImageView(final int drawableId,
			LayoutParams params, final int mapId) {
		ImageView iv = new ImageView(getActivity());
		iv.setClickable(true);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)v.getContext()).getIntent().putExtra("file_map", String.valueOf(mapId));
				Log.d("pirateMadness", "player choose map " + mapId);
			}
		});
		iv.setLayoutParams(params);
		iv.setImageResource(drawableId);
		return iv;
	}
	
	private ImageView getPreconfiguredImageView(final int drawableId,
			LayoutParams params, final int playerId) {
		ImageView iv = new ImageView(getActivity());
		iv.setClickable(true);
		iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity)v.getContext()).getIntent().putExtra("pirate" + playerId + "_drawable", drawableId);
				Log.d("pirateMadness", "player " + playerId + " choose drawableId " + drawableId);
			}
		});
		iv.setLayoutParams(params);
		iv.setImageResource(drawableId);
		return iv;
	}

	private void setButtonOnListener(final Bundle savedInstanceState, View v) {
		final FragmentManager fm = getFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();
//		Button bPlay = (Button) v.findViewById(R.id.button_play);
//		bPlay.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				ft.replace(android.R.id.content, new FragmentGame());
//				ft.addToBackStack(null);
//				ft.commit();
//			}
//		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// utilisé saveParam pour ajouter les données
		saveParam(outState, getView());
	}

	public Bundle saveParam(Bundle data, View v) {
		if (data == null) {
			data = new Bundle();
		}
		EditText edit1 = (EditText) v.findViewById(R.id.name_player1);
		String text = edit1.getText().toString();
		// tester si le text vaut null lorsque l'utilisateur ne renseigne rien
		if (text.length() == 0) {
			text = new String("Player1");
		}
		Log.d("PiratesMadness - SettingsActivity", "text player1 vaut : "
				+ text);
		data.putString("namePlayerOne", text);

		EditText edit2 = (EditText) v.findViewById(R.id.name_player2);
		String text2 = edit2.getText().toString();
		// tester si le text vaut null lorsque l'utilisateur ne renseigne rien
		if (text2.length() == 0) {
			text2 = new String("Player2");
		}
		Log.d("PiratesMadness - SettingsActivity", "text player2 vaut : "
				+ text2);
		data.putString("namePlayerTwo", text2);

		RadioButton modeEasy = (RadioButton) v.findViewById(R.id.easy_mode);
		String textMode = null;
		if (modeEasy.isChecked()) {
			textMode = "easy";
		} else {
			textMode = "hard";
		}
		Log.d("PiratesMadness", "mode : "
				+ ((modeEasy.isChecked() == true) ? "easy" : "hard"));
		data.putString("mode", textMode);

		RadioButton soundOn = (RadioButton) v
				.findViewById(R.id.button_sound_on);
		boolean sound = false;
		if (soundOn.isChecked()) {
			sound = true;
		}
		Log.d("PiratesMadness", "sound : " + sound);
		data.putBoolean("sound", sound);
		return data;
	}
}
