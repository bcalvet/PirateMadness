//package fr.upem.piratesmadness;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//public class Initializer extends Fragment{
//	public Initializer() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreateView(inflater, container, savedInstanceState);
//		View v = inflater.inflate(R.layout.fragment_initializer, null);
//		final MainActivity act = (MainActivity)getActivity();
//		final Button exit = (Button)v.findViewById(R.id.fr_upem_piratesmadness_BattleGroundInitialize_button);
//		exit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				act.asyncTask.cancel(true);
//				act.asyncTask=null;
//				System.exit(-1);
//			}
//		});
//		MainActivity activity = (MainActivity)getActivity();
//		Log.d("PiratesMadness","activity : "+activity);
//		//---------------------------------------- A MODIFIER ---------------------------------
//		Bundle extras = activity.getIntent().getExtras();
//		if(extras==null){
//			extras = new Bundle();
//		}
//		extras.putInt("mode", 1);
//		activity.getIntent().putExtras(extras);
//		extras.putString("pirate1_drawable", "pirate1");
//		extras.putString("pirate2_drawable", "pirate2");
//		//-------------------------------------------------------------------------------------
//		act.asyncTask= new BattleGroundInitializer(activity);
//		act.asyncTask.execute("1");
//		return v;
//	}
//	
//}
