package fr.upem.piratesmadness;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {
	BattleGroundInitializer asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        this.getFragmentManager().beginTransaction().add(android.R.id.content, new FragmentMain()).commit();  
    }

    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if(getIntent().getExtras()!=null && getIntent().getExtras().getBoolean("reload")){
    		getIntent().getExtras().putBoolean("reload", false);
    		final FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(android.R.id.content, new FragmentGame());
			ft.addToBackStack(null);
			ft.commit();
    	}
    }
}
