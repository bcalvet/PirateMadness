package fr.upem.piratesmadness;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
		hit(p1,p2);
	}

	private void fall(Pirate p1) {
		Log.d("PiratesMadness","fall");
		// With set speedAcceleration 1.1, speedAcceleration increase to 1.6 by step 0.1
		p1.speedAcceleration=(float)1.1;
		p1.noGravity=true;
	}

	private void bounce(Pirate p1){
		Log.d("PiratesMadness","bounce");
		switch(p1.direction){
		case NORTH : p1.direction = Direction.SOUTH; break;
		case SOUTH : p1.direction = Direction.NORTH; break;
		case EAST : p1.direction = Direction.WEST; break;
		case WEST : p1.direction = Direction.EAST; break;
		}
		//Security : check if the pirate bug - postTraitement
		if(p1.direction.isOpposite(p1.gravity)){
			p1.direction.randomDirection(p1);
		}
	}

	private boolean changeGravity(Pirate p1, Rect rec){
		Direction tmp = checkGravity(p1, rec);
		boolean result = changeDirection(p1, tmp);
		return result;
	}

	private Direction checkGravity(Pirate p, Rect r){
		Rect r2 = p.getPirateBuffer();
		if(r2.bottom>r.top && r2.right>=r.left && r2.top<r.bottom && r2.left<r.left)
			return Direction.EAST;
		if(r2.bottom>=r.top && r2.right>r.left && r2.left<r.right && r2.top<r.top)
			return Direction.SOUTH;
		if(r2.top<=r.bottom && r2.left<r.right && r2.right>r.left && r2.bottom>r.bottom)
			return Direction.NORTH;
		return Direction.WEST;
	}

	private boolean hitWall(Rect obstacle, Pirate p1){
		Rect bufferOfPirate = p1.getPirateBuffer();
		if(Rect.intersects(obstacle, bufferOfPirate)){
			//When intersects first time after jump set twiceJump true, because the player can rejump. But he as only one chance
			p1.twiceJump++;
			if(!p1.isActually(obstacle)){
				//hit a wall when jumping
				if(p1.noGravity && changeGravity(p1,obstacle)){
					p1.noGravity = false;
					p1.setActually(obstacle);
					//Correction of the pirateBuffer position.
					Log.d("PiratesMadness","pirate corrections - g : "+p1.gravity+", d : "+p1.direction);
					switch (p1.gravity) {
					case NORTH:
						p1.coordinate.y=obstacle.bottom+(bufferOfPirate.height()/2)-1;
						break;
					case SOUTH:
						p1.coordinate.y=obstacle.top-(bufferOfPirate.height()/2)+1;
						break;
					case EAST:
						p1.coordinate.x=obstacle.left-(bufferOfPirate.width()/2)+1;
						break;
					case WEST:
						p1.coordinate.x=obstacle.right+(bufferOfPirate.width()/2)-1;
						break;
					}
					//Set speedAcceleration 1
					p1.speedAcceleration=1;
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
		//we don't want that he falls, but just moves. And anticipated the next movement
		if(p1.noGravity){
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
		if(Rect.intersects(p1.getPirateBuffer(), p2.getPirateBuffer())){
			if(p1.speed+Math.abs(p1.speedAcceleration)>p2.speed+Math.abs(p2.speedAcceleration)){
				p2.life--;
			}
			if(p1.speed+Math.abs(p1.speedAcceleration)<p2.speed+Math.abs(p2.speedAcceleration)){
				p1.life--;
			}
			bounce(p1);
			bounce(p2);
			Log.d("PiratesMadness", "Life p1 : "+p1.life+"; life p2 : "+p2.life);
		}
	}

	static boolean isInThisInterval(float min, float val, float max){
		return min<=val && val<=max;
	}

	public boolean changeDirection(Pirate p, Direction newGravity){
		if(newGravity!=p.gravity && !p.gravity.isOpposite(newGravity)){
			if(p.speedAcceleration<0){
				p.direction = p.gravity.oppositeDirection(p);
			}else{
				p.direction = p.gravity;
			}
		}
		int rotation = 0;
		switch(newGravity.ordinal()-p.gravity.ordinal()){
		case 2 : case -2 :
			rotation = 180;
			break;
		case -1 : case 3 :
			rotation = 3*90;
			break;
		case 1 : case -3 :
			rotation = 90;
			break;
		}
		if(rotation!=0){
			Matrix m = new Matrix();
			m.postRotate(rotation);
			p.texture = Bitmap.createBitmap(p.texture, 0, 0,
					p.texture.getWidth(), p.texture.getHeight(), m, false);
		}
		p.gravity = newGravity;
		return true;
	}
}