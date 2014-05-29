package fr.upem.piratesmadness;

import java.util.Random;

import android.text.format.Time;

public enum Direction {
	NORTH {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==SOUTH)return true;
			return false;
		}

		@Override
		public void randomDirection(Pirate p) {
			Random random = new Random();
			Time time = new Time();
			time.setToNow();
			//Adding 1 avoid illegalArgumentException in nextInt due to 0.
			if(random.nextInt(time.second+1)%2==0){
				p.direction=WEST;
			}else{
				p.direction=EAST;
			}
		}

		@Override
		public Direction oppositeDirection() {
			// TODO Auto-generated method stub
			return Direction.SOUTH;
		}
	}
	,EAST {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==WEST) return true;
			return false;
		}

		@Override
		public void randomDirection(Pirate p) {
			Random random = new Random();
			Time time = new Time();
			time.setToNow();
			//Adding 1 avoid illegalArgumentException in nextInt due to 0.
			if(random.nextInt(time.second+1)%2==0){
				p.direction=NORTH;
			}else{
				p.direction=SOUTH;
			}
		}

		@Override
		public Direction oppositeDirection() {
			// TODO Auto-generated method stub
			return Direction.WEST;
		}
	}
	,SOUTH {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==NORTH) return true;
			return false;
		}

		@Override
		public void randomDirection(Pirate p) {
			Random random = new Random();
			Time time = new Time();
			time.setToNow();
			//Adding 1 avoid illegalArgumentException in nextInt due to 0.
			if(random.nextInt(time.second+1)%2==0){
				p.direction=WEST;
			}else{
				p.direction=EAST;
			}
		}

		@Override
		public Direction oppositeDirection() {
			// TODO Auto-generated method stub
			return Direction.NORTH;
		}
	}
	,WEST {
		@Override
		public boolean isOpposite(Direction d) {
			if(d==EAST) return true;
			return false;
		}

		@Override
		public void randomDirection(Pirate p) {
			Random random = new Random();
			Time time = new Time();
			time.setToNow();
			//Adding 1 avoid illegalArgumentException in nextInt due to 0.
			if(random.nextInt(time.second+1)%2==0){
				p.direction=NORTH;
			}else{
				p.direction=SOUTH;
			}	
		}

		@Override
		public Direction oppositeDirection() {
			// TODO Auto-generated method stub
			return Direction.EAST;
		}
	};
	

	abstract public boolean isOpposite(Direction d);
	abstract public void randomDirection(Pirate p);
	abstract public Direction oppositeDirection();
	static Direction random(Random random){
		int val = random.nextInt((int)System.currentTimeMillis())%4;
		switch (val) {
		case 0:
			return Direction.NORTH;
		case 1:
			return Direction.EAST;
		case 2:
			return Direction.SOUTH;
		default :
			return Direction.WEST;
		}
	}
}
