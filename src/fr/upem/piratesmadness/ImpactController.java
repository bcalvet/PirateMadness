package fr.upem.piratesmadness;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;

public class ImpactController {

	public void update(BattleGround battleGround){
		Pirate p1 = battleGround.arrayPirates.get(0);
		Pirate p2 = battleGround.arrayPirates.get(1);
		ArrayList<Bonus> bonus = battleGround.arrayBonus;
		boolean p1NotFalling = false;
		boolean p2NotFalling = false;
		ArrayList<Rect> obstacles = battleGround.obstacles;
		p1NotFalling = false;
		for(int i = 0; i<obstacles.size();i++){
			//test if the there is a collision with a wall
			Rect obstacle = obstacles.get(i);
			p1NotFalling|=hitWall(obstacle, p1);
			p2NotFalling|=hitWall(obstacle, p2);
			for(int j=0;j<bonus.size(); j++){
				bonusIntersection(obstacle, bonus.get(j));
				if(bonus.get(j).consumeEffect(p1)){
					bonus.remove(j);
				}
				if(j<bonus.size())
					if(bonus.get(j).consumeEffect(p2)){
						bonus.remove(j);
					}
			}
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
		//		Log.d("PiratesMadness","fall");
		// With set speedAcceleration 1.1, speedAcceleration increase to 1.6 by step 0.1
		p1.speedAcceleration=(float)1.1;
		p1.noGravity=true;
	}

	private void bounce(Pirate p1){
		//		Log.d("PiratesMadness",p1.name+" bounce");
		switch(p1.direction){
		case NORTH : p1.direction = Direction.SOUTH; break;
		case SOUTH : p1.direction = Direction.NORTH; break;
		case EAST : p1.direction = Direction.WEST; break;
		case WEST : p1.direction = Direction.EAST; break;
		}
		//Security : check if the pirate bug - postTraitement
		//		if(p1.direction.isOpposite(p1.gravity)){
		//			p1.direction.randomDirection(p1);
		//		}
	}

	private boolean changeGravity(Pirate p1, Rect rec){
		//		Log.d("PiratesMadness", "changeGravity old "+p1.name+" - gravity : "+p1.gravity+"; direction : "+p1.direction);
		Direction tmp = checkGravity(p1, rec);
		boolean result = changeDirection(p1, tmp);
		//		Log.d("PiratesMadness", "changeGravity new "+p1.name+" - gravity : "+p1.gravity+"; direction : "+p1.direction);
		return result;
	}

	private Direction checkGravity(Pirate p, Rect obstacle){
		Rect pirateBuffer = p.getPirateBuffer();

		//We compare if the pirate is inside the wall with a possible value max set p.speed
		int value = Math.round(p.speed);
		//		Log.d("PiratesMadness", "changeGravity value : "+value);

		boolean directionOk=false;
		//Create a new Rect (intersection) with coordinates of the intersection.
		Rect intersection = new Rect(pirateBuffer);
		//		Log.d("PiratesMadness", "changeGravity before- rect pirate : "+intersection.flattenToString()+", obstacle : "+obstacle.flattenToString());
		intersection.intersect(obstacle);
		//		Log.d("PiratesMadness", "changeGravity after- rect pirate : "+intersection.flattenToString()+", obstacle : "+obstacle.flattenToString());
		//To know if we intersect in the direction we compare width and height of the new Rect (intersection)

		//		Log.d("PiratesMadness","height and width : "+intersection.height()+" ? "+intersection.width());

		switch (p.direction) {
		case NORTH:
			//Don't forgive to inverse values due to the order (min, val, max)
			if(isInThisInterval(obstacle.bottom-value, pirateBuffer.top, obstacle.bottom)
					&& intersection.height()<=intersection.width()){
				//				Log.d("PiratesMadness", "direction NORTH ok");
				directionOk=true;
			}
			break;
		case SOUTH:
			if(isInThisInterval(obstacle.top, pirateBuffer.bottom, obstacle.top+value)
					&& intersection.height()<intersection.width()){
				//				Log.d("PiratesMadness", "direction SOUTH ok");
				directionOk=true;
			}
			break;
		case EAST:			
			if(isInThisInterval(obstacle.left, pirateBuffer.right, obstacle.left+value) 
					&& intersection.width()<intersection.height()){
				//				Log.d("PiratesMadness", "direction EAST ok");
				directionOk=true;
			}
			break;
		case WEST:			
			if(isInThisInterval(obstacle.right-value, pirateBuffer.left, obstacle.right)
					&& intersection.width()<intersection.height()){
				//				Log.d("PiratesMadness", "direction WEST ok");
				directionOk=true;
			}
			break;
		}

		//If it's the direction
		if(directionOk){
			//			Log.d("PiratesMadness", "Return direction");
			return p.direction;
		}
		//Else if (jump) : it's not the direction and he is in ascending state, so it must be the opposite of its gravity
		else{
			if(p.speedAcceleration<0){
				//				Log.d("PiratesMadness", "Return opposite to gravity");
				return p.gravity.oppositeDirection();
			}
			//Else : he is falling, so it's the currently gravity
			else{
				//				Log.d("PiratesMadness", "Return gravity");
				return p.gravity;
			}
		}
	}

	private boolean hitWall(Rect obstacle, Pirate p1){
		Rect bufferOfPirate = p1.getPirateBuffer();
		if(Rect.intersects(obstacle, bufferOfPirate)){
			//When intersects first time after jump set twiceJump true, because the player can rejump. But he as only one chance
			p1.twiceJump++;
			if(!p1.isCurrently(obstacle)){
				//hit a wall when jumping
				if(p1.noGravity && changeGravity(p1,obstacle)){
					p1.noGravity = false;
					p1.setCurrently(obstacle);
					//Correction of the pirateBuffer position.
					//					Log.d("PiratesMadness","pirate corrections - g : "+p1.gravity+", d : "+p1.direction);
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
		//		Log.d("PiratesMadness",p1.name+" isPerpendicular");
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

	private void hit(final Pirate p1, final Pirate p2){
		if(Rect.intersects(p1.getPirateBuffer(), p2.getPirateBuffer())){
			if(p1.speed+Math.abs(p1.speedAcceleration)>p2.speed+Math.abs(p2.speedAcceleration)){
				p2.life--;
				p2.ga.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(p1.ga, "Pirate "+p2.name+" loose a life", Toast.LENGTH_SHORT).show();
					}
				});
			}
			if(p1.speed+Math.abs(p1.speedAcceleration)<p2.speed+Math.abs(p2.speedAcceleration)){
				p1.life--;
				p1.ga.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(p1.ga, "Pirate "+p1.name+" loose a life", Toast.LENGTH_SHORT).show();
					}
				});
			}

			bounce(p1);
			bounce(p2);
			IAController ia = new IAController();
			//Correction of pirates's positions - pirate who has lost a life moves back
			while(Rect.intersects(p1.getPirateBuffer(), p2.getPirateBuffer())){
				ia.move(p1);
				ia.move(p2);
			}
			Log.d("PiratesMadness", "Life p1 : "+p1.life+"; life p2 : "+p2.life);
		}
	}

	static boolean isInThisInterval(float min, float val, float max){
		return min<=val && val<=max;
	}

	public boolean changeDirection(Pirate p, Direction newGravity){
		//		Log.d("PiratesMadness",p.name+" changeDirection");
		if(newGravity!=p.gravity && !p.gravity.isOpposite(newGravity)){
			if(p.speedAcceleration<0){
				p.direction = p.gravity.oppositeDirection();
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

	public boolean bonusIntersection(Rect obstacle, Bonus b){
		if(!b.noGravity){
			return true;
		}else{
			if(Rect.intersects(obstacle, b.getBuffer())){
				b.noGravity=false;
				return true;
			}
			return false;
		}
	}
}