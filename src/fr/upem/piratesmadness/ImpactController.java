package fr.upem.piratesmadness;

import java.util.ArrayList;

import android.graphics.Rect;
import android.util.Log;

public class ImpactController {

	public void update(BattleGround battleGround){
		Pirate p1 = battleGround.arrayPirates.get(0);
		Pirate p2 = battleGround.arrayPirates.get(1);
		boolean p1NotFalling = false;
		boolean p2NotFalling = false;
		ArrayList<Rect> obstacles = battleGround.obstacles;
		p1NotFalling = false;
		for(int i = 0; i<obstacles.size();i++){
			//test if the there is a collision with a wall
			p1NotFalling|=hitWall(obstacles.get(i), p1);
			p2NotFalling|=hitWall(obstacles.get(i), p2);
			//if noGravity et hitWall()==false : falling
			//test if the pirates hit each other
			//			hit();
			//test if there is a wall or not under the pirate : falling mode or pirate receipt mode

		}
		if(!p1NotFalling)
			fall(p1);
		if(!p2NotFalling)
			fall(p2);
	}

	private void fall(Pirate p1) {
		p1.speedAcceleration=(float)1.6;
		p1.noGravity=true;
	}

	private void bounce(Pirate p1){
		switch(p1.direction){
		case NORTH : p1.direction = Direction.SOUTH; break;
		case SOUTH : p1.direction = Direction.NORTH; break;
		case EAST : p1.direction = Direction.WEST; break;
		default : p1.direction = Direction.EAST; break;
		}
	}

	private void changeGravity(Pirate p1, Rect rec){
//		float xDelta = p1.getPirateBuffer().centerX()-rec.exactCenterX();
//		float yDelta = p1.getPirateBuffer().centerY()-rec.exactCenterY();
//		Log.d("PiratesMadness","xDelta : "+xDelta+" = pirate("+p1.getPirateBuffer().centerX()+") - rect("+rec.exactCenterX()+")");
//		Log.d("PiratesMadness","yDelta : "+yDelta+" = pirate("+p1.getPirateBuffer().centerY()+") - rect("+rec.exactCenterY()+")");
//		if(xDelta==1 && yDelta!=1){
//			p1.gravity = Direction.WEST;
//		}else if(xDelta==-1 && yDelta!=1){
//			p1.gravity = Direction.EAST;
//		}else if(xDelta!=1 && yDelta==1){
//			p1.gravity = Direction.SOUTH;
//		}else if(xDelta!=1 && yDelta==-1){
//			p1.gravity = Direction.NORTH;
//		}
		Log.d("PiratesMadness","Ancienne gravity : "+p1.gravity);
		if(/*isInThisInterval(rec.left, p1.getPirateBuffer().centerX(), rec.right) &&*/ p1.getPirateBuffer().centerY()<rec.centerY()){
			p1.gravity = Direction.SOUTH;
		}else if(/*isInThisInterval(rec.left, p1.getPirateBuffer().centerX(), rec.right) && */p1.getPirateBuffer().centerY()>rec.centerY()){
			p1.gravity = Direction.NORTH;
		}
		else if(/*isInThisInterval(rec.top, p1.getPirateBuffer().centerY(), rec.bottom) && */p1.getPirateBuffer().centerX()<rec.centerX()){
			p1.gravity = Direction.EAST;
		}else if(/*isInThisInterval(rec.top, p1.getPirateBuffer().centerY(), rec.bottom) && */p1.getPirateBuffer().centerX()>rec.centerX()){
			p1.gravity = Direction.WEST;
		}else{
			Log.e("PiratesMadness", "Error when pirate change its gravity");
		}
		Log.d("PiratesMadness","New gravity : "+p1.gravity);
	}

	private boolean hitWall(Rect obstacle, Pirate p1){
		if(Rect.intersects(obstacle, p1.getPirateBuffer())){
			//If no gravity : jumping
			if(p1.noGravity){
				//hiqt a wall when jumping
				changeGravity(p1,obstacle);
				p1.noGravity = false;
			}
			//que le mur perpendiculaire à la gravité du pirate
			else{
				if(isPerpendicular(obstacle, p1)){
					bounce(p1);
				}
			}
			//Just move
			//que le mur perpendiculaire à la direction du pirate
			return true;
		}
		return false;
	}

	private boolean isPerpendicular(Rect obstacle, Pirate p1) {
		switch (p1.direction) {
		case NORTH:
			return isInThisInterval(obstacle.left, p1.getPirateBuffer().centerX(), obstacle.right);
		case SOUTH:
			return isInThisInterval(obstacle.left, p1.getPirateBuffer().centerX(), obstacle.right);
		case EAST:
			return isInThisInterval(obstacle.top, p1.getPirateBuffer().centerY(), obstacle.bottom);
		case WEST:
			return isInThisInterval(obstacle.top, p1.getPirateBuffer().centerY(), obstacle.bottom);
		default :
			Log.e("PiratesMadness","Error - pirate has no direction");
			return false;
		}
	}

	private void hit(Pirate p1, Pirate p2){

	}

	private static boolean isInThisInterval(float min, float val, float max){
		return min<=val && val<=max;
	}
}
