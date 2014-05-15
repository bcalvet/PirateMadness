package fr.upem.piratesmadness;

import java.util.ArrayList;

import android.graphics.Rect;
import android.util.Log;

public class IAController {


	private void move(Pirate pirate){
		if(pirate.noGravity){
			//Jumping : sometimes or Falling : sometimes
			switch (pirate.gravity) {
			case NORTH:
				pirate.coordinate.y-=pirate.speed*pirate.speedAcceleration;
				break;
			case WEST:
				pirate.coordinate.x+=pirate.speed*pirate.speedAcceleration;
				break;
			case EAST:
				pirate.coordinate.x-=pirate.speed*pirate.speedAcceleration;
				break;
			case SOUTH:
				pirate.coordinate.y+=pirate.speed*pirate.speedAcceleration;
				break;
			}
			//change speedAcceleration
			if(pirate.speedAcceleration!=-1.6){
				pirate.speedAcceleration-=0.4;
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
	
	private void startingToJump(Pirate p){
		p.speedAcceleration=(float) 1.5;
	}

	private boolean checkWall(ArrayList<Rect> obstacles, Pirate p1){
		//Test if there is a wall in front of the IA's player
		//Test if there is a wall under the player (else it's failing)
		return false;
	};
	
	private boolean searchNearestWall(ArrayList<Rect> obstacles, Pirate p1){
		return false;
	}
	
	private void showInformation(BattleGround bg){
		for(Pirate p : bg.arrayPirates){
			Log.d("PiratesMadness", "Pirate : "+p.name+"; coordinate, width :"+p.coordinate.x+"; height : "+p.coordinate.y+"; buffer : "+p.padBuffer.flattenToString());
			Log.d("PiratesMadness","speed : "+p.speed+"; speed accélération : "+p.speedAcceleration+"; Direction : "+p.direction+"; gravity : "+p.gravity);
		}
		for(Rect r : bg.obstacles){
			Log.d("PiratesMadness","obstacle : "+r.flattenToString());
		}
	}
	
	private void initPirate(BattleGround bg){
		//Initialisaion of speed, direction
		int i=0;
		int j=0;
		int numberOfObstacles = bg.obstacles.size();
		int numberOfPirate = bg.arrayPirates.size();
		Pirate p;
		for(i=0;i<numberOfPirate;i++){
			p = bg.arrayPirates.get(i);
			p.speed=1;
			p.life=3;
			p.noGravity=false;
			
			//Need to determinate what is the gravity direction. After this we can choose the direction.
			
			for(j=0; j<numberOfObstacles; j++){
				if(bg.obstacles.get(j).bottom==p.coordinate.y+p.texture.getHeight()){
					;
				}
				else if(bg.obstacles.get(i).top==p.coordinate.y){
					;
				}
				else if(bg.obstacles.get(i).right==p.coordinate.x+p.texture.getWidth()){
					;
				}
				else if(bg.obstacles.get(i).left==p.coordinate.x){
					;
				}
			}
//			bg.arrayPirates.get(i).gravity;
//			bg.arrayPirates.get(i).direction;
		}
		//Gravity is already initialized because the PNG is in the great position
	}

	public void update(BattleGround battleGround){
		//DEBUG 
		showInformation(battleGround);
		
		int numberOfPirates = battleGround.arrayPirates.size();
		
		for(int i=0; i<numberOfPirates; i++){
			//If there is a jump : check direction and gravity; due to two variables changed
//				checkWall(battleGround.obstacles, battleGround.arrayPirates.get(i), battleGround.arrayPirates.get(i).direction);
		}
		
		
		//Check if pirates are against a wall

		//Check if pirates hit together

		//Check if speedAcceleration is finished or not And so the gravity
	};

	
	
	//If pirate is jumping : direction =  gravity
	public void changeDirection(Pirate p, Direction newGravity){
		if(!p.direction.isOpposite(newGravity)){
			p.direction = p.gravity;
		}
		p.gravity = newGravity;
	}
	
	
}
