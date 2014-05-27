package fr.upem.piratesmadness;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterAdapter extends ArrayAdapter<CharacterItem> {
	
	public CharacterAdapter(Context applicationContext,
			int layoutPrototypeImageText, int prototypeText,
			CharacterItem[] createListOfCharacter) {
		// TODO Auto-generated constructor stub
		super(applicationContext,layoutPrototypeImageText,prototypeText,createListOfCharacter);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = super.getView(position, convertView, parent);
		
		CharacterItem item = (CharacterItem) getItem(position);
		ImageView iv = (ImageView) view.findViewById(R.id.prototype_image);
		iv.setImageResource(item.getId());
		TextView tx = (TextView) view.findViewById(R.id.prototype_text);
		tx.setText(item.getText());
		
		return (View) view;
	}

}
