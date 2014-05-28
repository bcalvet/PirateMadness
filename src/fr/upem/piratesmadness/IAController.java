package fr.upem.piratesmadness;

import java.util.ArrayList;

import android.util.Log;

public class IAController {


	private void move(Pirate pirate){
		if(pirate.noGravity){
			if(pirate.speed>=0)pirate.actualy=-1;
				
			//Jumping : sometimes or Falling : sometimes
			switch (pirate.gravity) {
			case NORTH:
				pirate.coordinate.y-=pirate.speed*pirate.speedAcceleration;
				break;
			case WEST:
				pirate.coordinate.x-=pirate.speed*pirate.speedAcceleration;
				break;
			case EAST:
				pirate.coordinate.x+=pirate.speed*pirate.speedAcceleration;
				break;
			case SOUTH:
				pirate.coordinate.y+=pirate.speed*pirate.speedAcceleration;
				break;
			}
			//Hack : needed to use variable speedAcceleration with value set 1
			if(pirate.speedAcceleration==1 && pirate.noGravity){
				pirate.speedAcceleration+=0.1;
			}
			//change speedAcceleration
			if(pirate.speedAcceleration!=1.6 && pirate.speedAcceleration!=1 && pirate.speedAcceleration!=-1.6){
				pirate.speedAcceleration+=0.1;
			}
		}
		switch (pirate.direction) {
		//Normally : always
		case NORTH:
			pirate.coordinate.y-=pirate.speed;
			break;
		case WEST:
			pirate.coordinate.x-=pirate.speed;
			break;
		case EAST:
			pirate.coordinate.x+=pirate.speed;
			break;
		case SOUTH:
			pirate.coordinate.y+=pirate.speed;
			break;
		}
	}
	
	public void startingToJump(Pirate p){
		p.speedAcceleration=(float) -1.6;
		p.twiceJump=0;
		if(p.twiceSpeedAcceleration){
			Log.d("PiratesMadness","TwiceJump ok");
			p.speedAcceleration+=(float) -1.6;
			//Pirate can't use double jump twice
			p.twiceJump=10;
		}
		p.twiceSpeedAcceleration=false;
		p.noGravity=true;
	}

	public void update(BattleGround battleGround){
		//DEBUG 
//		showInformation(battleGround);
		
		int numberOfPirates = battleGround.arrayPirates.size();
		ArrayList<Pirate> arrayPirate = battleGround.arrayPirates;
		
		for(int i=0; i<numberOfPirates; i++){
			//If there is a jump : check direction and gravity; due to two variables changed
			move(arrayPirate.get(i));
		}
	}
	
}
