package fr.upem.piratesmadness;

import java.util.ArrayList;

import android.util.Log;

public class IAController {


	void move(Pirate pirate){
		//		Log.d("PiratesMadness",pirate.name+" move");
		if(pirate.noGravity){

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
			//			//Hack : needed to use variable speedAcceleration with value set 1
			//			if(pirate.speedAcceleration==1){
			//				pirate.speedAcceleration+=0.1;
			//			}
			//change speedAcceleration
			if(pirate.speedAcceleration<1.6){
				pirate.speedAcceleration+=0.1;
			}
			//After pirate moves in the air, we stop the condition with currently
			//			Log.d("PiratesMadness", "speedAcceleration : "+pirate.speedAcceleration+"; speed total : "+pirate.speed*pirate.speedAcceleration);
			//Becarefull : float is not sure. You can't say : pirate.speedAcceleration==0.
			if(pirate.speedAcceleration>0){
				pirate.currently=-1;
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
		Log.d("PiratesMadness", p.name+" starting to jump");
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
			moveBonus(battleGround.arrayBonus);
		}
	}

	public void moveBonus(ArrayList<Bonus> bonus){
		int size = bonus.size();
		Bonus b;
		for(int i=0; i<size; i++){
			b = bonus.get(i); 
			if(b.noGravity){
				switch (b.gravity) {
				case NORTH:
					b.coordinate.y-=3;
					break;
				case SOUTH:
					b.coordinate.y+=3;
					break;
				case EAST:
					b.coordinate.x+=3;
					break;
				case WEST:
					b.coordinate.x-=3;
					break;
				}
			}
		}
	}

}
