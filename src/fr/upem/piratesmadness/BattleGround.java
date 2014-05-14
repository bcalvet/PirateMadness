package fr.upem.piratesmadness;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class BattleGround {
	ArrayList<Rect> obstacles;
	Bitmap texture;
	MediaPlayer sound;
	//Useful : indicates if the map is more higher than wider
	boolean isLandscape;
	int width;
	int height;
	int difficulty;
	ArrayList<Pirate> arrayPirates;
}
