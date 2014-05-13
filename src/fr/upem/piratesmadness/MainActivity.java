package fr.upem.piratesmadness;


import fr.upem.piratesmadness.R;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	BattleGroundInitializer asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        this.getFragmentManager().beginTransaction().add(android.R.id.content, new FragmentMain()).commit();  
    }

}
