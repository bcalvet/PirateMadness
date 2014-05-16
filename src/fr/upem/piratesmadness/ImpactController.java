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
		p1.speedAcceleration=(float)-1.2;
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
		float xDelta = p1.getPirateBuffer().centerX()-rec.exactCenterX();
		float yDelta = p1.getPirateBuffer().centerY()-rec.exactCenterY();
		if(xDelta==1 && yDelta!=1){
			p1.gravity = Direction.WEST;
		}else if(xDelta==-1 && yDelta!=1){
			p1.gravity = Direction.EAST;
		}else if(xDelta!=1 && yDelta==1){
			p1.gravity = Direction.SOUTH;
		}else if(xDelta!=1 && yDelta==-1){
			p1.gravity = Direction.NORTH;
		}
	}

	private boolean hitWall(Rect obstacle, Pirate p1){
		if(obstacle.intersect(p1.getPirateBuffer())){
			if(p1.noGravity){
				//hit a wall when jumping
				changeGravity(p1,obstacle);
				p1.noGravity = false;
			}else{
				//hit a wall when walking
				bounce(p1);
			}
			return true;
		}
		return false;
	}


	private void hit(Pirate p1, Pirate p2){

	}
}
