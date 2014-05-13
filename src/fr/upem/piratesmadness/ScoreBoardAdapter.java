package fr.upem.piratesmadness;

import fr.upem.piratesmadness.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScoreBoardAdapter extends ArrayAdapter<LineScoreBoard>{

	public ScoreBoardAdapter(Context context, int resource, int ressourceText,
			LineScoreBoard[] objects) {
		super(context, resource, ressourceText,objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		if(convertView!=null){
			v = convertView;
		}else{
			v = super.getView(position, convertView, parent);
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
		tim.setTextSize(20);
		TextView scor = (TextView) v.findViewById(R.id.id_prototype_text_score);
		scor.setText(item.score);
		scor.setEms(size);
		scor.setTextSize(20);
		
		return (View)v;
	}

}
