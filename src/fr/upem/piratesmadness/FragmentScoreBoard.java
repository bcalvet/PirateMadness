package fr.upem.piratesmadness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FragmentScoreBoard extends ListFragment {

	public FragmentScoreBoard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<LineScoreBoard> list = new ArrayList<LineScoreBoard>();
		try {
			Scanner s = new Scanner(getActivity().getAssets().open("score"));
			while (s.hasNextLine()) {
				String[] line = s.nextLine().split(" ");
				list.add(new LineScoreBoard(line[0], line[1], line[2], line[3]));
			}
		} catch (IOException ioe) {
			// TODO
		}
		this.setListAdapter(new ArrayAdapter<LineScoreBoard>(getActivity(), R.layout.item_line_score_board, list){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v;
				if(convertView!=null){
					v = convertView;
				}else{
					v = getActivity().getLayoutInflater().inflate(R.layout.item_line_score_board, null);
				}
				LineScoreBoard item = getItem(position);
				int size=100;
				TextView win = (TextView) v.findViewById(R.id.id_prototype_text_name_winner);
				win.setText(item.winner);
				win.setEms(size);
				win.setTextSize(20);
				TextView loos = (TextView) v.findViewById(R.id.id_prototype_text_name_looser);
				loos.setText(item.looser);
				loos.setEms(size);
				loos.setTextSize(20);
				TextView tim = (TextView) v.findViewById(R.id.id_prototype_text_time);
				tim.setText(item.time);
				tim.setEms(size);
				tim.setTextSize(15);
				TextView scor = (TextView) v.findViewById(R.id.id_prototype_text_score);
				scor.setText(item.score);
				scor.setEms(size);
				scor.setTextSize(20);
				return v;
			}
		});
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		v.setBackgroundColor(getResources().getColor(R.color.Black));
		return v;
	}
}
