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
		Log.d("PiratesMadness","fall");
		p1.speedAcceleration=(float)1.6;
		p1.noGravity=true;
	}

	private void bounce(Pirate p1){
		Log.d("PiratesMadness","bounce");
		switch(p1.direction){
		case NORTH : p1.direction = Direction.SOUTH; break;
		case SOUTH : p1.direction = Direction.NORTH; break;
		case EAST : p1.direction = Direction.WEST; break;
		default : p1.direction = Direction.EAST; break;
		}
	}

	private boolean changeGravity(Pirate p1, Rect rec){
		Log.d("PiratesMadness","changeGravity");
		Log.d("PiratesMadness","Ancienne gravity : "+p1.gravity+ " direction : "+p1.direction);
		Direction tmp = checkGravity(p1, rec);
		Log.d("PiratesMadness", "checkGravity : "+tmp);
		boolean result = changeDirection(p1, tmp);
		Log.d("PiratesMadness","New gravity : "+p1.gravity+ " direction : "+p1.direction);
		return result;
	}

	private Direction checkGravity(Pirate p, Rect r){
		Log.d("PiratesMadness","checkGravity");
		Rect r2 = p.getPirateBuffer();
		//		Log.d("Pirates", "r2.bottom = "+r2.bottom+ "	r.top = "+ r.top);
		//		Log.d("Pirates", "r2.right = "+r2.right + "	r.left = "+r.left);
		//		Log.d("Pirates", "r2.left = "+r2.left+ "	r.right = " + r.right);
		//		Log.d("Pirates", "r2.top = "+r2.top + "	r.bottom = "+r.bottom);
		if(r2.bottom>r.top && r2.right>=r.left && r2.top<r.bottom && r2.left<r.left)
			return Direction.EAST;
		if(r2.bottom>=r.top && r2.right>r.left && r2.left<r.right && r2.top<r.top)
			return Direction.SOUTH;
		if(r2.top<=r.bottom && r2.left<r.right && r2.right>r.left && r2.bottom>r.bottom)
			return Direction.NORTH;
		return Direction.WEST;
	}


	private boolean hitWall(Rect obstacle, Pirate p1){
//		Log.d("PiratesMadness","hitWall");
		if(Rect.intersects(obstacle, p1.getPirateBuffer())){
			if(!p1.isActually(obstacle)){
				//If no gravity : jumping
				if(p1.noGravity){
					//hit a wall when jumping
					if(changeGravity(p1,obstacle)){
						p1.noGravity = false;
						p1.setActually(obstacle);
						switch (p1.gravity) {
						case NORTH:
							p1.coordinate.y=obstacle.bottom+p1.getPirateBuffer().height();
							break;
						case SOUTH:
							p1.coordinate.y=obstacle.top-p1.getPirateBuffer().height();
							break;
						case EAST:
							p1.coordinate.x=obstacle.left-p1.getPirateBuffer().width();
							break;
						case WEST:
							p1.coordinate.x=obstacle.right+p1.getPirateBuffer().width();
							break;
						}
					}
				}
				//que le mur perpendiculaire à la gravité du pirate
				else{
					if(isPerpendicular(obstacle, p1)){
						bounce(p1);
					}
				}
			}
			//Just move
			//que le mur perpendiculaire à la direction du pirate
			return true;
		}
		//If the pirate intersects anything but its variable noGravity is setting to true, in that case
		//we don't want that he falls, but just moves.
		if(p1.noGravity){
			return true;
		}
		return false;
	}

	private boolean isPerpendicular(Rect obstacle, Pirate p1) {
		Log.d("PiratesMadness","isPerpendicular");
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

	public boolean changeDirection(Pirate p, Direction newGravity){
		//!p.direction.isOpposite(newGravity) && 
		//		if(p.gravity == newGravity && p.speedAcceleration<0){
		//			//TODO : nothing; it's because the pirate intersects the block where he is already.
		//			return false;
		//		}
		if(newGravity != p.gravity && !p.gravity.isOpposite(newGravity)){
			p.direction = p.gravity;
		}
		p.gravity = newGravity;
		return true;
	}
}