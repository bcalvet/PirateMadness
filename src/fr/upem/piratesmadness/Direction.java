package fr.upem.piratesmadness;

import java.util.Random;

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
			if(random.nextInt(8)%2==0){
				p.direction=WEST;
			}else{
				p.direction=EAST;
			}
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
			if(random.nextInt(8)%2==0){
				p.direction=NORTH;
			}else{
				p.direction=SOUTH;
			}
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
			if(random.nextInt(8)%2==0){
				p.direction=NORTH;
			}else{
				p.direction=SOUTH;
			}	
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
			if(random.nextInt(8)%2==0){
				p.direction=WEST;
			}else{
				p.direction=EAST;
			}
		}
	};
	

	abstract public boolean isOpposite(Direction d);
	abstract public void randomDirection(Pirate p);
}
