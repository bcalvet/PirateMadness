package fr.upem.piratesmadness;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends Activity {
	MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context c = getApplicationContext();
        media=MediaPlayer.create(c, R.raw.the_medallion_calls);
        media.setLooping(true);
//        media.start();
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
			getIntent().putExtra("player1", "Player1");
			getIntent().putExtra("player2", "Player2");
			getIntent().putExtra("file_map", "1");
		}
		//init
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
		media.pause();
		media.stop();
		media.release();
    }
    
    
}
