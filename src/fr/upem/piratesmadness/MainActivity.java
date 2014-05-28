package fr.upem.piratesmadness;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        this.getFragmentManager().beginTransaction().add(android.R.id.content, new FragmentMain()).commit();
    }

    @Override
    protected void onResume() {
    	super.onResume();
		//init
		if(getIntent().getExtras()==null || getIntent().getExtras().getString("file_map")==null){
	    	getIntent().putExtra("mode", 1);
			getIntent().putExtra("pirate1_drawable", R.drawable.pirate1);
			getIntent().putExtra("pirate2_drawable", R.drawable.pirate2);
			getIntent().putExtra("file_map", "1");
		}
		//init
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
