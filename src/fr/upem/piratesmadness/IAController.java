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
		p.noGravity=true;
	}
	
	
	private void showInformation(BattleGround bg){
		for(Pirate p : bg.arrayPirates){
			Log.d("PiratesMadness", "Pirate : "+p.name+"; coordinate, width :"+p.coordinate.x+"; height : "+p.coordinate.y+"; buffer : "+p.padBuffer.flattenToString());
			Log.d("PiratesMadness","speed : "+p.speed+"; speed accélération : "+p.speedAcceleration+"; Direction : "+p.direction+"; gravity : "+p.gravity);
		}
//		for(Rect r : bg.obstacles){
//			Log.d("PiratesMadness","obstacle : "+r.flattenToString());
//		}
	}
	

	public void update(BattleGround battleGround){
		//DEBUG 
//		showInformation(battleGround);
		
		int numberOfPirates = battleGround.arrayPirates.size();
		ArrayList<Pirate> arrayPirate = battleGround.arrayPirates;
		
		for(int i=0; i<numberOfPirates; i++){
			//If there is a jump : check direction and gravity; due to two variables changed
//				checkWall(battleGround.obstacles, battleGround.arrayPirates.get(i), battleGround.arrayPirates.get(i).direction);
			move(arrayPirate.get(i));
		}
		
		
		//Check if pirates are against a wall

		//Check if pirates hit together

		//Check if speedAcceleration is finished or not And so the gravity
	}

		
//	//If pirate is jumping : direction =  gravity
//	public void changeDirection(Pirate p, Direction newGravity){
//		if(!p.direction.isOpposite(newGravity)){
//			p.direction = p.gravity;
//		}
//		p.gravity = newGravity;
//	}
	
	
}
